(defproject seajure "1.0.0-SNAPSHOT"
  :description "The web site for Seajure, the Seattle Clojure Group."
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [enlive "1.0.0"]
                 [compojure "0.6.2"]
                 [swank-clojure "1.4.0-SNAPSHOT"]
                 [ring/ring-jetty-adapter "0.3.9"]]
  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]]
  :main seajure.web)
