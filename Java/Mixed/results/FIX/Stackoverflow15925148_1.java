class Stackoverflow15925148_1{
void vulnerable(){
try {  
            f = new PrintWriter(new BufferedWriter(new FileWriter(fileName,true)));//issue at this line  
            synchronized(this) {  
                f.println(new StringBuffer(dateString).append("<").append(Thread.currentThread().getName()).append(">").append(traceLog).toString());  
            }  

            checkFileSize();  
        }catch(IOException e){    
            e.printStackTrace();  
        } finally {
             if (f != null) {
                 f.close();
             }
        }
//Also if you are using JDK-7, have a look at try-with-resources.
}
}