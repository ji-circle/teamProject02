package com.example.msggrid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.msggrid.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

// [3] SecondFragment -> Activity (FragmentDataListener 인터페이스 구현.. 아래줄)
class MainActivity : AppCompatActivity(), FragmentDataListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //변경한 코드. 프래그먼트의 데이터 전달, 보내는 부분
        binding.run {
            fragment1Btn.setOnClickListener {
                // [1] Activity -> FirstFragment
                val dataToSend = "Hello First Fragment! \n From Activity"
                //FragmentBtn1 클릭 리스너 안에서 FirstFragment의 인스턴스를 생성하고,
                //  newInstance 메소드에 데이터를 설정해 생성된 프래그먼트에 전달함
                val fragment = list.newInstance(dataToSend)
                //생성한 프래그먼트를 setFragment 안에 넣어서 프래그먼트를 설정, 화면에 표시함
                setFragment(fragment)
            }
            fragment2Btn.setOnClickListener {
                //여기부터 추가할 부분...
                val filteredData = MemoryStorage.users.filter { it.isLike }
                val fragment = grid.newInstance(ArrayList(filteredData))
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainer, fragment)
//                    .addToBackStack(null)
//                    .commit()

                //여기가 추가 끝


                // [1] Activity -> SecondFragment
                //val dataToSend = "Hello Second Fragment!\n From Activity"
                //val fragment = grid.newInstance(dataToSend)
                setFragment(fragment)
            }
            btnAlarm.setOnClickListener {
                notification()
            }
        }

        //프로그램이 시작될 때 FirstFragment를 뿌리라고 코드 작성함
        setFragment(list())
    }

    private fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 26 버전 이상

            // 추가한 코드... 사용자 권한 요청 부분. 설정으로 안내함
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                    // 알림 권한이 없다면, 사용자에게 권한 요청
                    // Setting 는 android.provider 로 선택하기
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    }
                    startActivity(intent)
                }
            }


            val channelId = "one-channel"
            val channelName = "My Channel One"

            //채널을 하나 만든다. NotificationChannel로.
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

                // 채널에 다양한 정보 설정
                description = "My Channel One Description"

                // 빨간색으로 1, 2 뜨는거...라는데 뭘까
                setShowBadge(true)

                //알람 울리게. 링톤
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                //오디오 들어가 있는거... 이건 기본 소리니까 바꾸고 싶으면 mp3 다운받아 바꾸기
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

                //사운드에 오디오 넣기
                setSound(uri, audioAttributes)

                //진동 넣을건지
                enableVibration(true)
            }
            // 채널을 manager를 통해 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성, builder 통해 채널 아이디 넣어줌
            builder = NotificationCompat.Builder(this, channelId)

        } else {
            // 26 버전(8.0) 이하라면
            builder = NotificationCompat.Builder(this)
        }

        //사진 넣기
        //이부분은 이미지 바꾸기... 예시임...
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_profile_default)

        //알림이 울렸을 때... 두 번째 액티비티 실행하는 코드
        //val intent = Intent(this, SecondActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //val pendingIntent = PendingIntent.getActivity(
        //    this,
        //    0,
        //    intent,
        //    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        //)
        // 알림의 기본 정보... 빌더의 옵션들 ??
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            //알람 발생 시각 : 현재 시각
            setWhen(System.currentTimeMillis())
            setContentTitle("새로운 알림입니다.")
            setContentText("알림이 잘 보이시나요.")
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("이것은 긴텍스트 샘플입니다. 아주 긴 텍스트를 쓸때는 여기다 하면 됩니다.이것은 긴텍스트 샘플입니다. 아주 긴 텍스트를 쓸때는 여기다 하면 됩니다.이것은 긴텍스트 샘플입니다. 아주 긴 텍스트를 쓸때는 여기다 하면 됩니다.")
            )
            //라지아이콘에 사진 넣기
            setLargeIcon(bitmap)
            //위의 setStyle을 지우고... 이부분은 글 대신 사진을 크게 넣는 코드
//            setStyle(NotificationCompat.BigPictureStyle()
//                    .bigPicture(bitmap)
//                    .bigLargeIcon(null))  // hide largeIcon while expanding
            //걔를 눌렀을 때 펜딩인텐트(intent... secondActivity 호출하는...)
            //addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }

        //permission 추가해주기!
        //알림 실행
        manager.notify(11, builder.build())
        //Log.d("Notification", "실행")
    }


    private fun setFragment(frag: Fragment) {
        //동적
        //setFragment가 불리면 전달받은 프래그먼트로 전환하는 것
        supportFragmentManager.commit {
            //frameLayout에 전달받은 프래그먼트를 뿌릴 것임
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    // [3] SecondFragment -> Activity
//MainActivity에서 해당 인터페이스 상속받고, 그 안에 구현체도 있어야 함...
// FragmentDataListener 리스너를 걸어두고, 그 리스너를 통해 onDataReceived가 발생하면
//   그 안으로 스트링 데이터가 들어올거고, 그걸 토스트로 띄울 것임. (onDataReceived를 override 해서 구현체 만들었음)
    override fun onDataReceived(data: String) {
        // Fragment에서 받은 데이터를 처리
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }
}
