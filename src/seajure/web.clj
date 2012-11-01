(ns seajure.web
  (:use [net.cgrand.enlive-html :only [deftemplate content set-attr do->
                                       clone-for]]
        [clojure.java.io :only [copy file]]))

(def assets ["style.css" "logo.png"])

(defn get-resource-stream [path]
  (.getResourceAsStream (clojure.lang.RT/baseLoader) path))

(defn read-resource [path]
  (-> (get-resource-stream path)
      (java.io.InputStreamReader.)
      (java.io.PushbackReader.)
      (read)))

(defn member-links [members]
  (clone-for [{:keys [name url projects]} members]
    [:a] (do-> (content name)
               (set-attr :href url))))

(deftemplate index "index.html" [members]
  [:ul.members :li] (member-links members))

(defn -main
  ([out]
     (.mkdirs (file out))
     (let [lines (index (read-resource "members.clj"))]
       (doseq [f assets] (copy (get-resource-stream f) (file out f)))
       (spit (file out "index.html") (apply str lines))))
  ([] (-main "public")))
