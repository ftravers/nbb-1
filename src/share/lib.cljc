(ns share.lib)
(defn get-failed [state env]
  (let [e (-> @state env :ms)
        failed (reduce
                (fn [acc [k v]]
                  (when (= false v)
                    (conj acc k)))
                []
                e)]
    failed)
  ;; 
  )

(comment
  (def state
    (r/atom
     {:stg
      {:ms {:rs-ok true
            :pr-ok false
            :pns-ok true
            :es-ok true}}}))
  (get-failed state :stg)
  ;;
  )
