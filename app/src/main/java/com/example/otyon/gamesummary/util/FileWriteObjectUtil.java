package com.example.otyon.gamesummary.util;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ファイルへオブジェクトを書き出すユーティリティ
 */
public class FileWriteObjectUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    ////////////////////////////////////////////////////////////////////////////////
    // ■ 永続化
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * シリアライズしてファイルに保存
     * @param context   コンテキスト
     * @param serializeObject シリアライズ可能なオブジェクト
     * @param filename 書き込むファイル名
     * @return  true:保存成功 false:保存失敗
     */
    public boolean save(Context context, Object serializeObject, String filename) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(serializeObject);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }finally{
            try {
                if ( oos!=null ) oos.close();
                if ( fos!=null ) fos.close();
            }catch(IOException ex ) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * ファイルからデシリアライズしてインスタンス生成
     * ※ファイルが存在シない場合は、初期値でインスタンスを生成
     * @param context   コンテキスト
     * @param filename ファイルネーム
     * @return 生成されたインスタンス
     */
    static public Object load(Context context, String filename) {
        Object instance = null;   // 生成するインスタンス

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(filename);
            ois = new ObjectInputStream(fis);
            instance =  ois.readObject();
        } catch (Exception ex) {
        }finally{
            try {
                if ( ois!=null ) ois.close();
                if ( fis!=null ) fis.close();
            }catch(IOException ex ) {
                ex.printStackTrace();
            }
        }

        return instance;
    }


    ////////////////////////////////////////////////////////////////////////////////
    // ■ ファイル操作
    ////////////////////////////////////////////////////////////////////////////////

    /**
     // ファイル削除
     * @param context   コンテキスト
     */
    public void delete(Context context, String filename) {
        context.deleteFile(filename);
    }
}