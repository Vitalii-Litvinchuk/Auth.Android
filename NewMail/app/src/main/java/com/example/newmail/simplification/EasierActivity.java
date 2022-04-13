package com.example.newmail.simplification;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newmail.MainActivity;
import com.example.newmail.R;
import com.example.newmail.account.LoginActivity;
import com.example.newmail.account.RegisterActivity;
import com.example.newmail.account.UsersActivity;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.network.request.RequestService;
import com.example.newmail.simplification.interfaces.AuthActions;

public class EasierActivity extends AppCompatActivity
        implements AuthActions {
    private Menu menu;
    private static int openedId = R.id.m_main;
    private static boolean isGroupAuthVisible = !HomeApplication.getInstance().isAuth();
    private static boolean isGroupActionVisible = HomeApplication.getInstance().isAuth();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        setGroupsVisible(menu);
        menu.findItem(openedId).setVisible(false);
        this.menu = menu;
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

    @Override
    public void auth() {
        openedId = R.id.m_main;
        isGroupAuthVisible = false;
        isGroupActionVisible = true;
    }

    public void logout() {
        isGroupAuthVisible = true;
        isGroupActionVisible = false;
    }

    @Override
    public void setGroupsVisible(Menu menu) {
        menu.setGroupVisible(R.id.groupAuth, isGroupAuthVisible);
        menu.setGroupVisible(R.id.groupActions, isGroupActionVisible);
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
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
                HomeApplication.getInstance().deleteToken();
                RequestService.resetInstance();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.m_main:
                openedId = R.id.m_main;
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
