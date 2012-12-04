(ns techmesh-cljs.repl.core
  (:refer-clojure :exclude [==])
  (:use-macros [clojure.core.match.js :only [match]]
               [clojure.tools.macro :only [symbol-macrolet]]
               [cljs.core.logic.macros :only
                [run run* == fresh conde all defne]])
  (:use [cljs.core.logic :only [membero firsto conso]])
  (:require [clojure.browser.repl :as repl]
            [domina :as d]
            [domina.css :as dc]))

(repl/connect "http://localhost:9000/repl")

;; -----------------------------------------------------------------------------
;; Truth

(comment
  (if false
    true
    false)
  
  (if nil
    true
    false)

  (if 0
    true
    false)

  (if ""
    true
    false)
  )

;; -----------------------------------------------------------------------------
;; Scope!

(comment
 (def fns (atom []))
 
 (dotimes [i 5]
   (swap! fns conj (fn [] i)))

 (map (fn [f] (f)) @fns)
 )

;; -----------------------------------------------------------------------------
;; Unified interaction with collections

(comment
  (first '(1 2 3))

  (first [1 2 3])

  (first #{1 2 3})

  (first {:foo 'bar})

  (first "foo")

  (doseq [x [1 2 3]]
    (println x))

  (doseq [[k v :as kv] {:foo 'bar :baz 'woz}]
    (println k v kv))
  )

;; -----------------------------------------------------------------------------
;; Sensible equality!

(comment
  (cljs.core/= [1 2 3] [1 2 3])
  
  (cljs.core/= {:foo 'bar} {:foo 'bar})
  )

;; -----------------------------------------------------------------------------
;; Collections are functions

(comment
  (def address
    {:street "101 Bit Ave."
     :city "Bit City"
     :zip "10101"})
  
  (map address [:city :zip])
  )

;; -----------------------------------------------------------------------------
;; Destructuring

(comment
  (let [[_ x _] ["foo" "bar" "baz"]]
    x)

  (let [[_ [c] _] ["foo" "bar" "baz"]]
    c)

  (let [[_ {[c] :bar} _] ["foo" {:bar "woz"} "baz"]]
    c)
  )

;; -----------------------------------------------------------------------------
;; Multi-arity fns

(defn ^:export foo [a b] (+ a b))

(defn ^:export bar
  ([a b] (+ a b))
  ([a b c] (+ a b c)))

(defn time-bar []
  (time
    (dotimes [_ 1e7]
      (bar 1 2))))

(comment
  ;; little hack so we can get the fast calling convention
  (time
    (dotimes [_ 1e7]
      (js/techmesh-cljs.repl.core.foo 1 2)))

  (time-bar)
  )

;; -----------------------------------------------------------------------------
;; Interactive Development

(comment
  (def box (.getElementById js/document "box"))

  (set! (.. box -style -backgroundColor) "blue")
  (set! (.. box -style -height) "300px")
  (set! (.. box -style -height) "100px")

  (set! (.. box -className) "spin")
  (set! (.. box -className) "")

  (extend-type js/HTMLElement
    IFn
    (-invoke
      ([this k]
         (.getAttribute this k))))

  (map box ["id" "class"])

  (extend-type js/HTMLElement
    ILookup
    (-lookup
      ([this k]
         (-lookup this k v nil))
      ([this k not-found]
         (let [attr (name k)]
           (if-not (.hasAttribute this attr)
             not-found
             (.getAttribute this attr))))))
  
  (let [[id class] ((juxt :id :class) box)]
    class)

  (let [{[c] :class} box]
    c)
  )

;; /////////////////////////////////////////////////////////////////////////////
;; SLIDES
;; /////////////////////////////////////////////////////////////////////////////

;; -----------------------------------------------------------------------------
;; Pattern matching

(defn ^:export red-black [n]
  (match [n]
    [(:or [:black [:red [:red a x b] y c] z d]
          [:black [:red a x [:red b y c]] z d]
          [:black a x [:red [:red b y c] z d]]
          [:black a x [:red b y [:red c z d]]])] [:red [:black a x b] y [:black c z d]]
    :else n))

(comment
  (red-black [:black [:red [:red 1 2 3] 3 4] 5 6])
  (red-black [:black [:black [:red 1 2 3] 3 4] 5 6])

  (let [n [:black [:red [:red 1 2 3] 3 4] 5 6]]
    (time
      (dotimes [_ 500000]
        (red-black n))))
  )

;; /////////////////////////////////////////////////////////////////////////////
;; SLIDES
;; /////////////////////////////////////////////////////////////////////////////