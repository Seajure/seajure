(ns seajure.server
  (:use [ring.adapter.jetty]
        [seajure.web :only [index read-resource]]))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (index (read-resource "members.clj")
                (read-resource "projects.clj"))})

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (run-jetty app {:port port})))

;;; http://brehaut.net/blog/2011/ring_introduction
