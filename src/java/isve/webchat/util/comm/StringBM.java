/*
 * Copyright 2016 yuwang881@gmail.com.
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
package isve.webchat.util.comm;

/**
 *
 * @author yuwang881@gmail.com
 */
import java.io.UnsupportedEncodingException;
import static java.lang.Math.max;

/**
 *
 * @author wangyu
 */
public class StringBM {

    private String pattern;
    private byte[] bytes_pattern;
    private boolean status;
    private int[] BCArray; //Bad Charactors Array
    private int[] GCArray; //Good Charactor Array
    
    public StringBM() {
    }

    private void prepareBCArray() {
        //BCArray[i] i is unmatch charactor from text
        BCArray = new int[256];
        int plength = bytes_pattern.length;
        for (int i =0;i<256; i++){
            BCArray[i] = plength;
        }
        
        for (int i =0;i<plength; i++){
            BCArray[bytes_pattern[i]] = plength-1-i;
        }
    }
    
    private void prepareGCArray(){
        //GCArray[i] i is unmatch charactor position from pattern
         int plength = bytes_pattern.length;
         GCArray = new int[plength];
         int[] temp = new  int[plength]; //shared suffix position 
         
         for (int i=plength-1;i>=0;i--){
             //current matched suffix is from  position i to plength-1, length is plength-i 
       
             for (int j = 0; j < i; j++) {
                 if(temp[j] == plength - i-1 )
                 {
                    if (j-temp[j] >=0) if (bytes_pattern[j-temp[j]] == bytes_pattern[i])  temp[j]++;
                 }
             }
                 
             int position = 0;
             for (int j = 0; j < i; j++)  if (temp[j] == plength - i)  position = j;
      
             if (position != 0) {
                 temp[i] = position;
             } else {
                 int lastvalue = 0;
                 if (i < plength - 1)  lastvalue = temp[i + 1];
                 for (int j = 0; j <= i; j++)  temp[j] = lastvalue;
                 break;
             }      
         }
         
      
         // now temp[i] contain the position of the start index of max length sunfix of the last (i~length-1) substring
         
         for (int i=0;i<plength-1;i++){
             GCArray[i] =plength-temp[i+1]-1;
         }
         
         
         
    }
    
    public boolean init(String p) throws UnsupportedEncodingException {
        if (p != null)  pattern = p;
        return init();
    }
    
    public boolean init() throws UnsupportedEncodingException {
        status = false;
        if (pattern != null) {
            bytes_pattern = pattern.getBytes("UTF8");
             prepareBCArray();
             prepareGCArray();
             status = true;
        }
        return status;
    }
    
    public StringBM(String init_pattern) {
        if (init_pattern != null) pattern = init_pattern;
    }
    
    
    
    public int indexOf(String text, int offset) throws UnsupportedEncodingException{
        byte[] buffer = text.getBytes("UTF8");
        return indexOf(buffer,offset);
    }
    
    public int indexOf(byte[] buffer, int offset) throws UnsupportedEncodingException{
        if(status == false) return -1;
        
        int wlength = buffer.length;
        int plength = bytes_pattern.length;
        
        if (wlength < plength) return -1;
        
        // pointT point to the text; pointP point to pattern 
        int pointT = plength -1 + offset;
        int pointP;
        
      
        while(pointT < wlength){
            for (pointP =plength -1 ;pointP>=0; pointP--){
                if(buffer[pointT-plength+1+pointP] != bytes_pattern[pointP])  {
                    break;
                } else {
                    if (pointP ==0)   return pointT-plength+1;   //found
                }
            }
              
            // not match, get skip steps
            byte bad = buffer[pointT-plength+1+pointP];
            int stepforwardbad;
            if(bad>255 || bad <0){
                stepforwardbad = 0-plength+1+pointP;
            } else {
                stepforwardbad = BCArray[bad]-plength+1+pointP;
            }
            
            int stepforwardgood = GCArray[pointP];
            int stepforward = max(stepforwardbad,stepforwardgood);
            if (stepforward <= 0) stepforward =1;
            pointT= pointT+stepforward;
            if (pointT >wlength) return -1;
        }
        
        return -1;
              
        
    }
    
    
}
