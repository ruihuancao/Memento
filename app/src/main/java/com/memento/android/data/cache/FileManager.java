/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.memento.android.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FileManager {

  @Inject
  public FileManager() {}

  public void writeToFile(File file, String fileContent) {
    if (!file.exists()) {
      try {
        FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {

      }
    }
  }

  public String readFileContent(File file) {
    StringBuilder fileContentBuilder = new StringBuilder();
    if (file.exists()) {
      String stringLine;
      try {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((stringLine = bufferedReader.readLine()) != null) {
          fileContentBuilder.append(stringLine + "\n");
        }
        bufferedReader.close();
        fileReader.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return fileContentBuilder.toString();
  }


  public boolean exists(File file) {
    return file.exists();
  }


  public void clearDirectory(File directory) {
    if (directory.exists()) {
      for (File file : directory.listFiles()) {
        file.delete();
      }
    }
  }

  public void clearFile(File file){
    if(file.exists()){
      file.delete();
    }
  }

  public void writeToPreferences(Context context, String preferenceFileName, String key,
                                 long value) {

    SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
        Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putLong(key, value);
    editor.apply();
  }

  public long getFromPreferences(Context context, String preferenceFileName, String key) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
        Context.MODE_PRIVATE);
    return sharedPreferences.getLong(key, 0);
  }
}
