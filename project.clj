(defproject techmesh-cljs "0.1.0-SNAPSHOT"
  :description "TechMesh Conference Demo of ClojureScript"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.macro "0.1.1"]
                 [org.clojure/core.logic "0.8.0-beta2"]
                 [org.clojure/core.match "0.2.0-alpha10"]
                 [domina "1.0.0"]
                 [org.clojure/clojurescript "0.0-1535"]
                 [org.clojure/google-closure-library "0.0-2029"]
                 [org.clojure/google-closure-library-third-party "0.0-2029"]]

  :plugins [[lein-cljsbuild "0.2.9"]]
  
  :cljsbuild {:builds {:demo  {:source-path "src/techmesh-cljs/repl"
                               :compiler {:optimizations :advanced
                                          :output-to "demo.js"}}
                       :zebra {:source-path "src/techmesh-cljs/zebra"
                               :compiler {:optimizations :advanced
                                          :output-to "zebra.js"}}}})
