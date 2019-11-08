package com.grouph.recipelab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 좌측에서 끌어서 사용하는 네비게이션 뷰를 초기화하는 부분
        val navView: NavigationView = findViewById(R.id.nav_view)
        // 네비게이션 메뉴의 아이템을 클릭했을때 반응하기 위한 이벤트 리스너 설정
        navView.setNavigationItemSelectedListener(this@MainActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return true
    }
}
