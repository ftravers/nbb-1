(ns app
  (:require ["moment$default" :as moment]))

(+ 1 1)

(defn abcd [x] (+ 1 x))

(abcd 1)

(prn (js->clj (moment/max)))
( (.max (moment)))
;; => #object[Moment Wed Jan 25 2023 18:57:16 GMT-0700]
(let [a (moment/max)]
  (.toString a)) 
