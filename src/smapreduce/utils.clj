(ns smapreduce.utils
  (:gen-class))

(defn round-robin-map
  [full-list distribute-list]
  (let [grouped-values 
        (group-by first (mapv 
                          #(list %1 %2) 
                          (cycle distribute-list) 
                          full-list))] 
      (map (fn [map-args] 
             (let [distributed-item (first map-args)
                   full-list-for-item (map second (second map-args))]
               [distributed-item full-list-for-item]))
           grouped-values)))
