import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class clientss {

    public static int generateRandomInt(int num){
        Random random = new Random();
        return random.nextInt(num-1);
    }

    public static void main(String[] args) throws Exception, IOException {
        Socket s = new Socket("localhost", 50000);
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
       
        String username = System.getProperty("user.name");
        try{
        String client = "HELO\n";
        dout.write(client.getBytes());
        dout.flush();
        String serverInfo = in.readLine();
        System.out.println("Reply of HELO " + serverInfo);

        
        client = "AUTH " + username + "\n";
        dout.write(client.getBytes());
        dout.flush();
        serverInfo = in.readLine();

        client = "REDY\n";
        dout.write(client.getBytes());
        dout.flush();
        serverInfo = in.readLine();
        System.out.println("This is reply of REDY "+serverInfo);
        String jobDescription = serverInfo;
        String[] jobDesc = serverInfo.split(" ");
        System.out.println("This is jobbbbbbb "+ Arrays.toString(jobDesc));
        int jobNumber = Integer.parseInt(jobDesc[2]);
        int coreJob = Integer.parseInt(jobDesc[4]);
        int jobMem = Integer.parseInt(jobDesc[5]);
        int jobMemoo = Integer.parseInt(jobDesc[6]);

        System.out.println("This is a coreJob "+ coreJob);

        client = "GETS Capable "+ coreJob+" " + jobMem+" "+ jobMemoo+"\n";
        dout.write(client.getBytes());
        dout.flush();
        serverInfo = in.readLine();
        System.out.println("THis is gets capable "+serverInfo);
        String [] numservers = serverInfo.split(" ");
        String numServer = numservers[1];
        int serverNumber = Integer.parseInt(numServer);
        System.out.println("This is the reply of getsall "+serverInfo);
        System.out.println("Number of Server "+ serverNumber);

        String [] ServersData = new String [serverNumber];
        int ranNum = generateRandomInt(serverNumber);
        client = "OK\n";
        dout.write(client.getBytes());
        dout.flush();
        for(int i=0; i< serverNumber; i++){
            serverInfo = in.readLine();    
            ServersData[i] = serverInfo;    
        }
        //need to get more server list information
        //System.out.println("This is capable server list "+serverInfo);
        
        System.out.println("sever details "+ Arrays.toString(ServersData));
        

        List<String> newServerData = new ArrayList<String>(serverNumber);
        
        // for(String item: ServersData){
        //     String [] splitServersData = item.split(" ");
        //     List<String> updatedData = new ArrayList<>();
        //     updatedData.add(Arrays.toString(splitServersData));
        //     newServerData.addAll((ArrayList<String>) updatedData);
        // } 

        System.out.println("This is the dual arraylist "+ newServerData);



        String[] capableServer = ServersData[ranNum].split(" ");
        System.out.println("This is capableserver replty "+ Arrays.toString(capableServer));

        String serverName = capableServer[0];
        System.out.println("This is the name of server "+ serverName);
        int serNumDet = Integer.parseInt(capableServer[1]);
        //int serNumDet = 0;
     
        

        client = "OK\n";
        dout.write(client.getBytes());
        dout.flush();
        serverInfo = in.readLine();

        
        //schedules only first job..
        client = "SCHD 0"+serverName+" "+serNumDet+"\n";
        dout.write(client.getBytes());
        dout.flush();

        serverInfo = in.readLine();
        int incrementServer = 0;

        while(!serverInfo.contains("NONE")){

           client = "REDY\n";
           dout.write(client.getBytes());
           dout.flush();
           serverInfo = in.readLine();

           String jobDescpt = serverInfo;
           String [] jobDescpts = serverInfo.split(" ");
           int jobN = Integer.parseInt(jobDescpts[2]);
           int coreJ = Integer.parseInt(jobDescpts[4]);
           int jobM = Integer.parseInt(jobDescpts[5]);
           int jobMeo = Integer.parseInt(jobDescpts[6]);

           client = "GETS Capable "+ coreJ +" "+ jobM +" "+ jobMeo+"\n";
           dout.write(client.getBytes());
           dout.flush();
           serverInfo =in.readLine();

           String[] numberSer = serverInfo.split(" ");
           String numberServ = numberSer[1];
           int serNum = Integer.parseInt(numberServ);

           String[] ServerDatas = new String[serNum];
           int randNum = generateRandomInt(serNum);
           client ="OK\n";
           dout.write(client.getBytes());
           dout.flush();
           for(int i=0; i<serNum; i++){
               serverInfo = in.readLine();
               ServerDatas[i] = serverInfo;
           }

           List<String> newSerDta = new ArrayList<String>(serNum);
           String[] servercapable = ServerDatas[randNum].split(" ");
           String nameSer = servercapable[0];
           int serND = Integer.parseInt(servercapable[1]);

           client ="OK\n";
           dout.write(client.getBytes());
           dout.flush();
           serverInfo = in.readLine();



            if(jobDescpts[0].equals("JOBN")){
               
                //int jobN = Integer.parseInt(arr[2]);
                
                for(int i=0; i<serverNumber; i++){
                    
                    client = "SCHD "+ jobN +" "+ nameSer+ " "+serND+"\n";
                    dout.write(client.getBytes());
                    dout.flush();

                    
                }
               
                serverInfo = in.readLine();
            }
            else if(jobDescpts[0].equals("JCPN")){
                client = "REDY\n";
                dout.write(client.getBytes());
                dout.flush();
            }
        }

        //System.out.println("THis sis count "+ count);
        client = "QUIT\n";
        dout.write(client.getBytes());
        dout.flush();

        dout.close();
        s.close();
        }catch(UnknownHostException e){
            System.out.println("SOCK:" +e.getMessage());
        }catch(EOFException e){
            System.out.println("EOF:"+e.getMessage());
        }catch(IOException e){
            System.out.println("IO:"+e.getMessage());
        }
    }
}

