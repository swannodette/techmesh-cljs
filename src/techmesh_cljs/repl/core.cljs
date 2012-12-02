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
  (if 0
    true
    false)

  (if ""
    true
    false)
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
;; Unified Interfaces

;; -----------------------------------------------------------------------------
;; Persistent Data Structures

(comment
  (let [v '[foo]
        v' (conj v 'bar)]
    [v v'])
  )

;; -----------------------------------------------------------------------------
;; Interactive Development

(comment
  (def box (.getElementById js/document "box"))

  (set! (.. box -style -backgroundColor) "blue")
  (set! (.. box -style -height) "300px")
  (set! (.. box -style -height) "100px")

  (set! (.. box -className) "spin")
  )

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

;; -----------------------------------------------------------------------------
;; Logic Programming

(comment
  (run* [q]
    (== q true))

  (run* [q]
    (conde
      [(== q 'coffee)]
      [(== q 'tea)]))

  (run 1 [q]
    (conde
      [(== q 'coffee)]
      [(== q 'tea)]))

  (run* [q]
    (conso 'a '(b c) q))

  (run* [q]
    (conso 'a q '(a b c)))

  (run* [q]
    (conso q '(b c) '(a b c)))

  (run* [q]
    (fresh [x y]
      (conso x y q)))
  )
