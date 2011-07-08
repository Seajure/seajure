(ns seajure.server
  (:use [ring.adapter.jetty]
        [ring.middleware.file :only [wrap-file]]
        [seajure.web :only [index read-resource]]))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (index (read-resource "members.clj")
                (read-resource "projects.clj"))})

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (run-jetty (wrap-file app "resources") {:port port})))

;;; http://brehaut.net/blog/2011/ring_introduction
