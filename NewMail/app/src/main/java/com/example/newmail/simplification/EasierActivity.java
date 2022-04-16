package com.example.newmail.simplification;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newmail.MainActivity;
import com.example.newmail.R;
import com.example.newmail.account.LoginActivity;
import com.example.newmail.account.NoInternetActivity;
import com.example.newmail.account.RegisterActivity;
import com.example.newmail.account.UsersActivity;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.network.ConnectionDetector;

public class EasierActivity extends AppCompatActivity {
    private Menu menu;
    private static int openedId = R.id.m_main;
    private static boolean isGroupAuthVisible = !HomeApplication.getInstance().isAuth();
    private static boolean isGroupActionVisible = HomeApplication.getInstance().isAuth();

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnectingToInternet() && hasFocus){
            Intent intent = new Intent(this, NoInternetActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        setGroupsVisible(menu);
        menu.findItem(openedId).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isGroupAuthVisible) {
            return authGroup(item);
        }
        if (isGroupActionVisible) {
            return actionGroup(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void auth() {
        openedId = R.id.m_main;
        isGroupAuthVisible = false;
        isGroupActionVisible = true;
    }

    public void logout() {
        isGroupAuthVisible = true;
        isGroupActionVisible = false;
        HomeApplication.getInstance().deleteToken();
    }

    public void setGroupsVisible(Menu menu) {
        menu.setGroupVisible(R.id.groupAuth, isGroupAuthVisible);
        menu.setGroupVisible(R.id.groupActions, isGroupActionVisible);
    }

    public void openMainActivity() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean authGroup(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.m_register:
                openedId = R.id.m_register;
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                return true;
            case R.id.m_login:
                openedId = R.id.m_login;
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.m_main:
                openedId = R.id.m_main;
                openMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean actionGroup(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.m_users:
                openedId = R.id.m_users;
                intent = new Intent(this, UsersActivity.class);
                startActivity(intent);
                return true;
            case R.id.m_logout:
                openedId = R.id.m_main;
                logout();
                openMainActivity();
                return true;
            case R.id.m_main:
                openedId = R.id.m_main;
                openMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
