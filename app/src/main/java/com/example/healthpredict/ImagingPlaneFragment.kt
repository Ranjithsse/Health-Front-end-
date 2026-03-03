package com.example.healthpredict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class ImagingPlaneFragment : Fragment() {

    companion object {
        private const val ARG_PLANE_NAME = "plane_name"
        private const val ARG_IMAGE_RES = "image_res"

        fun newInstance(planeName: String, imageRes: Int): ImagingPlaneFragment {
            val fragment = ImagingPlaneFragment()
            val args = Bundle()
            args.putString(ARG_PLANE_NAME, planeName)
            args.putInt(ARG_IMAGE_RES, imageRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_imaging_plane, container, false)
        
        val ivPlaneImage = view.findViewById<ImageView>(R.id.ivPlaneImage)
        val seekBarSlice = view.findViewById<SeekBar>(R.id.seekBarSlice)
        val tvSliceNumber = view.findViewById<TextView>(R.id.tvSliceNumber)

        val planeName = arguments?.getString(ARG_PLANE_NAME) ?: ""
        val imageRes = arguments?.getInt(ARG_IMAGE_RES) ?: R.drawable.ic_splash_logo

        ivPlaneImage.setImageResource(imageRes)
        
        seekBarSlice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvSliceNumber.text = "Slice: $progress / ${seekBar?.max}"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        tvSliceNumber.text = "Slice: ${seekBarSlice.progress} / ${seekBarSlice.max}"

        return view
    }
}
