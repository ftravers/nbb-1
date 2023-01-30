(ns share.lib
  (:require [clojure.string :as s]))

(defn get-failed [state env]
  (let [e (-> @state env :ms)]
    (reduce
     (fn [acc [k v]]
       (if (= false v)
         (conj acc k)
         acc))
     []
     e)))

(defn clean-ms [ms]
  (-> ms
      name
      s/reverse
      s/join
      (subs 3)
      s/reverse
      s/join))

(comment
  (def state
    (atom
     {:stg
      {:ms {:rs-ok true
            :pr-ok false
            :pns-ok true
            :es-ok true}}}))
  (let [env :stg]
    (->> env
         (get-failed state)
         (mapv clean-ms)
         s/join))
  (clean-ms :pr-ok)
  ;;
  )
