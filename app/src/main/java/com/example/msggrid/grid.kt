package com.example.msggrid

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.msggrid.databinding.FragmentGridBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LIKED_LIST = "likedList"
//private const val ARG_PARAM2 = "param2"
private var likedList: List<User>? = null

// [3] SecondFragment -> Activity
interface FragmentDataListener {
    fun onDataReceived(data: String)
}


/**
 * A simple [Fragment] subclass.
 * Use the [grid.newInstance] factory method to
 * create an instance of this fragment.
 */
class grid : Fragment() {

    private var _binding: FragmentGridBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GridAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    //private var param2: String? = null


    // [3] SecondFragment -> Activity
    // 해당 인터페이스 타입의 리스너를 만들었음
    private var listener: FragmentDataListener? = null

    // [3] SecondFragment -> Activity
    // MainActivity와 SecondFragent를 연결해야 하니까... context 사용
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 프래그먼트가 액티비티에 붙을 때 액티비티가 이 인터페이스를 구현했는지 확인
        // context는 MainActivity에서 들어온 것

        //context를 검사해서 MainActivity 안에 FragmentDataListener가 구현되어있는지를 체크
        if (context is FragmentDataListener) {
            //이 리스너를 통해 호출하게 됨
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            likedList = it.getSerializable(ARG_LIKED_LIST) as? List<User>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGridBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment grid.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(filteredList: List<User>) =
            grid().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_LIKED_LIST, ArrayList(filteredList))

                    //둘 중 하나만 남기기
                    //putParcelableArrayList(ARG_LIKED_LIST, ArrayList<Parcelable>(filteredList.map{it as Parcelable}))
                    //putString(ARG_PARAM2, param2)
                }
            }


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [2] Fragment -> Fragment

        likedList?.let{ users ->
            val adapter = GridAdapter(users)

            binding.recyclerView.adapter = adapter

            //리사이클러뷰의 레이아웃 관리자를 설정해 3열의 그리드 형태로 레이아웃을 보여주는 역할을 함
            val layoutManager = GridLayoutManager(requireContext(),3)
            binding.recyclerView.layoutManager = layoutManager

        //            binding.gridview.adapter = adapter

        }
//        var likedList = arguments?.getParcelableArrayList<User>(ARG_LIKED_LIST)
        //binding.tvFrag2Text.text = param1

        // [3] SecondFragment -> Activity
        binding.btnSendActivity.setOnClickListener {
            val dataToSend = "Hello from SecondFragment!"
            //데이터를 액티비티에 전달
            //listener는 위에서 FragmentDataListener 타입으로 만들어놓고 MainActivity의 context가 할당된 상태.
            //  인터페이스의 onDataReceived 를 호출함, 그걸 상속받고 있는 메인에서 토스트가 뜸
            listener?.onDataReceived(dataToSend)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Binding 객체 해제
        _binding = null

        // [3] SecondFragment -> Activity
        listener = null
    }

}