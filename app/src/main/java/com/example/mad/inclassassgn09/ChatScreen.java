package com.example.mad.inclassassgn09;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatScreen extends Activity {
    ListView lv;
    ChatListAdapter cla;
    EditText editTextMessage;
    UserResponse userResponse;
    ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        lv = (ListView) findViewById(R.id.listViewChat);
        editTextMessage = (EditText) findViewById(R.id.editTextMessageToSend);
        messages = new ArrayList<Message>();

        if (getIntent().getExtras() != null) {
            userResponse = (UserResponse) getIntent().getExtras().getSerializable(MainActivity.USER_OBJ);

            FetchMessages(userResponse.getToken());
        }
    }

    public void FetchMessages(String token) {
        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/messages")
                .header("Authorization", "BEARER " + token)
                .build();

        MainActivity.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChatScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatScreen.this, "Invalid Id", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String messagesJson = response.body().string();
                    MessagesResponse messagesResponse = MainActivity.gson.fromJson(messagesJson, MessagesResponse.class);

                    messages.addAll(messagesResponse.getMessages());

                    if (messages.size() > 0) {
                        ChatScreen.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cla = new ChatListAdapter(ChatScreen.this, messages);
                                cla.setNotifyOnChange(true);
                                lv.setAdapter(cla);
                            }
                        });
                    } else {
                        ChatScreen.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChatScreen.this, "no messages in chat room", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    ChatScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatScreen.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void logoutAction(View view) {
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.putString(MainActivity.USER_OBJ, "");
        editor.apply();

        finish();
    }

    public void actionSendTextMessage(View view) {
        String textMessage = editTextMessage.getText().toString();
        if (!textMessage.equals("")) {
            AddNewMessages("TEXT", textMessage, "", userResponse.getToken());
        }

        editTextMessage.setText("");
    }

    public void AddNewMessages(String Type, String Comment, String FileThumbnailID, String token) {
        RequestBody formBody = new FormBody.Builder()
                .add("Type", Type)
                .add("Comment", Comment)
                .add("FileThumbnailID", FileThumbnailID)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/message/add")
                .header("Authorization", "BEARER " + token)
                .post(formBody)
                .build();

        MainActivity.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChatScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatScreen.this, "Invalid data passed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    //MessageResponse messageResponse = MainActivity.gson.fromJson(response.body().string(), MessageResponse.class);
                    FetchMessages(userResponse.getToken());
                } else {
                    ChatScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatScreen.this, "Unable to send message", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private static final int SELECT_PICTURE = 1;

    public void actionSendImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String value = "";

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = getBitmapFromUri(selectedImageUri, getContentResolver());
                    UploadImage(userResponse.getToken(), bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == RESULT_CANCELED) { //DO Nothing
        }
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private void UploadImage(String token, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        final byte[] imageBytes = baos.toByteArray();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("newFile", "newFile",
                        RequestBody.create(MEDIA_TYPE_PNG, imageBytes))
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization","BEARER " + token)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/add")
                .post(requestBody)
                .build();

        MainActivity.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChatScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatScreen.this, "Invalid data passed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    //MessageResponse messageResponse = MainActivity.gson.fromJson(response.body().string(), MessageResponse.class);

                    try {
                        JSONObject root = new JSONObject(response.body().string());
                        String status = root.getString("status");

                        if (status.equals("ok")) {
                            JSONObject file = root.getJSONObject("file");
                            String fileId = file.getString("Id");

                            AddNewMessages("IMAGE", "", fileId, userResponse.getToken());
                        } else {
                            ChatScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChatScreen.this, "Unable to upload image.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    ChatScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatScreen.this, "Unable to upload image.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public Bitmap getBitmapFromUri(Uri uri, ContentResolver cr) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = cr.openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
