package com.mycondo.a99hub24.adapters

import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder


class MainSliderAdapter : SliderAdapter() {
    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindImageSlide(position: Int, viewHolder: ImageSlideViewHolder) {
        when (position) {
            0 -> viewHolder.bindImageSlide("http://v2.bethub24.com/images/inplay-slide-1.jpg")
            1 -> viewHolder.bindImageSlide("http://v2.bethub24.com/images/inplay-slide-2.jpg")
            2 -> viewHolder.bindImageSlide("http://v2.bethub24.com/images/inplay-slide-3.jpg")
        }
    }
}