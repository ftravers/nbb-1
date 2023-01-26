(ns app
  (:require ["moment$default" :as mmt]
            ["inquirer$default" :as inq]
            [promesa.core :as p]))

(+ 1 1)

(defn abcd [x] (+ 1 x))

(abcd 1)

(prn (js->clj (mmt/max)))
(.max (mmt))
(let [a (mmt/max)]
  (prn (.toString a))
  (.toString a)) 

(def questions (clj->js [{:name "name"
                          :type "input"
                          :message "who are you?"}]))



(p/let [x (inq/prompt questions)]
  x)
