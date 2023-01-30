(ns app
  (:require ;; ["moment$default" :as mmt]
   ["ink" :refer [render Text Box useInput]]
   [reagent.core :as r]
   [share.lib :as l]
   [clojure.string :as s]
  ))

;; TODO: auto send commands to vterm to run nbb app.
(def state
  (r/atom
   {:dev {:check-roundtrip true
          :check-roundtrip-interval-sec 60
          :ms {:rs-ok true
               :pr-ok true
               :pns-ok true
               :es-ok true}}

    :stg {:check-roundtrip true
          :check-roundtrip-interval-sec 60
          :ms {:rs-ok true
               :pr-ok false
               :pns-ok false
               :es-ok true}}

    :prod {:check-roundtrip true
           :check-roundtrip-interval-sec 60
           :ms {:rs-ok true
                :pr-ok true
                :pns-ok true
                :es-ok true}}}))

(defn ok-status [state env]
  (let [e (-> @state env :ms)]
    ^{:key env}
    [:> Box
     [:> Box {:width 5}
      [:> Text env]]
     [:> Box
      (if (every? #(= true (val %)) e)
        [:> Text {:color "green"} "Everything is good."]
        [:> Text {:color "red"}
         (str "Failed MSs: "
              (->> env
                   (l/get-failed state)
                   (mapv l/clean-ms)
                   (s/join " ")))])]])
  )

()
(defn main [state]
  [:> Box {:flexDirection "column"}
   [:> Box
    [:> Box {:width 5} [:> Text {:color "blue"} "ENV"]]
    [:> Box [:> Text {:color "blue"} "OK Status"]]]
   (map #(ok-status state %) [:dev :stg :prod])
   ;; (useInput handle-input)


   ])

(render (r/as-element [main state]))

(defn main-loop [state]
  (js/setTimeout #(main-loop state) 3000)
  (swap! state update-in [:stg :ms :pr-ok] not))

(main-loop state)






