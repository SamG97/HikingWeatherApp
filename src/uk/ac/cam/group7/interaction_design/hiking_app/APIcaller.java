package uk.ac.cam.group7.interaction_design.hiking_app; /**
 * Created by Dávid on 2017.05.11.
 * Singleton class creating the owm client, it's only purpose is to
 * instantiate the client with our own API-key, and keep it a singleton.
 * Key hardcoded.
 **/

import org.bitpipeline.lib.owm.*;

public class APIcaller extends OwmClient {


    private static APIcaller sCaller;

    private APIcaller(){
        super();
    }

    public static APIcaller getClient(){
        if ( sCaller == null ){
            sCaller = new APIcaller();
            sCaller.setAPPID("d12c4a04b7d0170dff8f1afca1e4c0ff");
        }
        return sCaller;
    }

}
