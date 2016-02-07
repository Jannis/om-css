(ns om-css.devcards.core
  (:require-macros [devcards.core :as dc :refer [defcard deftest]]
                   [cljs.test :refer [is testing async]]
                   [om-css.dom :refer [defui]])
  (:require [devcards-om-next.core :as don :refer-macros [defcard-om-next]]
            [goog.dom :as gdom]
            [om.next :as om]
            [om-css.dom :as dom]
            [om-css.core :as oc]))

(def style-1
  {:text-align :center})

(defui Foo
  static oc/Style
  (style [_]
    [[:.root {:color "#FFFFF"}]
     [:.section (merge {} ;;style-1
                  {:background-color :green})]])
  Object
  (render [this]
    (dom/div {:id "ns-test"}
      (dom/div {:class :root} "div with class :root"
        (dom/section {:class :section} "section with class :section"
          (dom/p {:className "preserved"} "paragraph with class \"preserved\""))))))

(defcard-om-next foo-card
  Foo)

(deftest namespaced-classnames-in-dom
  (testing "classnames are namespace qualified"
    (let [c (gdom/getElement "ns-test")]
      (is (not (nil? (gdom/getElementByClass "om_css_devcards_core_root"))))
      (is (not (nil? (gdom/getElementByClass "om_css_devcards_core_section"))))
      (is (not (nil? (gdom/getElementByClass "preserved")))))))

(defui Bar
  oc/Style
  (style [_]
    [[:.bar {:margin "0 auto"}]])
  Object
  (render [this]
    (dom/div {:class :bar} "Bar component")))

(defcard-om-next bar-card
  Bar)

;; TODO:
;; - test `defui` that doesn't implement style
