(defproject techmesh-cljs "0.1.0-SNAPSHOT"
  :description "TechMesh Demo of ClojureScript"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/core.match "0.2.0-alpha11"]
                 [org.clojure/core.logic "0.8.0-beta3"]]

  :plugins [[lein-cljsbuild "0.2.9"]]

  
  :cljsbuild {:builds {:zebra {:source-path "src/techmesh-cljs/zebra"
                               :compiler {:optimizations :advanced
                                          :static-fns true
                                          :output-to "zebra.js"}}
                       :match {:source-path "src/techmesh-cljs/match"
                               :compiler {:optimizations :simple
                                          :pretty-print true
                                          :output-to "match.js"}}}})
