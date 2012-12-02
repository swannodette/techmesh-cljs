(defproject techmesh-cljs "0.1.0-SNAPSHOT"
  :description "TechMesh Conference Demo of ClojureScript"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/core.match "0.2.0-alpha10"]
                 [org.clojure/tools.macro "0.1.1"]
                 [org.clojure/core.logic "0.8.0-beta2"]]

  :plugins [[lein-cljsbuild "0.2.9"]]
  
  :cljsbuild {:builds {:repl  {:source-path "src/techmesh-cljs/repl"
                               :compiler {:optimizations :advanced
                                          :static-fns true
                                          :output-to "repl.js"}}
                       :zebra {:source-path "src/techmesh-cljs/zebra"
                               :compiler {:optimizations :advanced
                                          :static-fns true
                                          :output-to "zebra.js"}}
                       :match {:source-path "src/techmesh-cljs/match"
                               :compiler {:optimizations :simple
                                          :pretty-print true
                                          :output-to "match.js"}}}})
