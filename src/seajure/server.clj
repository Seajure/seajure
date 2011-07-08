(ns seajure.server
  (:use [ring.adapter.jetty]
        [ring.middleware.file :only [wrap-file]]
        [compojure.core :only [defroutes GET PUT]]
        [seajure.web :only [index read-resource]])
  (:require [compojure.route :as route]
            [swank.swank]
            [clojure.string :as string]))

(defonce hellos (ref []))

(defn list-hellos []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (interleave @hellos (repeat "\n"))})

(defn home [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (index (read-resource "members.clj")
                (read-resource "projects.clj"))})

(defroutes app
  (GET "/hellos" []
       (list-hellos))
  (PUT "/hellos/:text" [text]
       (dosync (commute hellos conj text))
       {:status 303
        :headers {"Location" "/hellos"}})

  (GET "/" [] home)
  (route/not-found "Four Oh Four"))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    (future (swank.swank/start-server))
    (run-jetty (wrap-file app "resources") {:port 5001})))

;;; http://brehaut.net/blog/2011/ring_introduction
