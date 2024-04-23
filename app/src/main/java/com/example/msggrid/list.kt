package com.example.msggrid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.msggrid.databinding.FragmentListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [list.newInstance] factory method to
 * create an instance of this fragment.
 */
class list : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment list.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            list().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
        //newInstance() 메서드는 프래그먼트가 생성될 때 데이터를 설정하고,
        // onCreate() 메서드는 프래그먼트가 초기화(onCreate)될 때 데이터를 읽어와 param1에 집어넣고,
        //  onViewCreated에서 해당 param1을 사용해 텍스트 바꿈
    }
    // [1] Activity -> FirstFragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //인자로 받은 데이터를 텍스트 뷰에 저장
        // view가 create 될 때 param1을 텍스트에 넣어줌
        binding.tvFrag1Text.text = param1

        // [2] Fragment -> Fragment
//        binding.btnGofrag2.setOnClickListener {
//            val dataToSend = "Hello Fragment2! \n From Fragment1"
//            //버튼 클릭 시 SecondFragment 의 새 인스턴스를 생성하고,
//            //  newInstance 메소드를 사용해 데이터를 전달한 후
//            val fragment2 = grid.newInstance(dataToSend)
//
//            //프래그먼트 트랜잭션을 통해 두 번째 프래그먼트를 시작함
//            // 프래그먼트에서 프래그먼트로!
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.frameLayout, fragment2)
//                .addToBackStack(null)
//                .commit()
//        }
    }

}





