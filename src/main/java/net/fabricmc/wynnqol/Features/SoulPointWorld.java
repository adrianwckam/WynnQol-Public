package net.fabricmc.wynnqol.Features;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.wynnqol.Utils.ChatUtils;
import net.fabricmc.wynnqol.Utils.Requests;
import java.text.SimpleDateFormat;
import java.util.*;

public class SoulPointWorld {
    private static class Server{
        public String World;
        public long TimeTillSP;
        public String TillSPString;
        Server(String World,Long time){
            this.World = World;
            this.TimeTillSP = 1200000-((System.currentTimeMillis() - time)%1200000);

            Date date = new Date(this.TimeTillSP);
            SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
            this.TillSPString = "&d"+World+" "+formatter.format(date);
        }

        public long getTimeTillSP(){
            return this.TimeTillSP;
        }
        public String getTillSPString(){
            return this.TillSPString;
        }
    }
    public static void init(){
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("spworld").executes(context -> {
                try {
                    SoulPointWorld.SendSoulPointWorld();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                return 0;
            }));
        }));
    }
    public static void SendSoulPointWorld() throws Exception {
            JsonObject rs = Requests.getAsJSON("https://athena.wynntils.com/cache/get/serverList");
            if(rs.get("servers") instanceof JsonNull) {
                ChatUtils.chatWithPrefix("&cUnable to get data from world uptime api");
                return;
            }

            List<Server> servers = new ArrayList<Server>();
            JsonObject ServerListJson = rs.get("servers").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : ServerListJson.entrySet()) {
                String World = entry.getKey();
                Long Uptime = entry.getValue().getAsJsonObject().get("firstSeen").getAsLong();
                servers.add(new Server(World, Uptime));
            }

            Collections.sort(servers, (o1, o2) -> {
                if(o1.getTimeTillSP() < (o2.getTimeTillSP())){
                    return -1;
                }else{
                    return 1;
                }
            });
            for (int i = 0; i < servers.size(); i++) {
                if(i>4) break;
                ChatUtils.chatWithPrefix(servers.get(i).getTillSPString());
            }

    }
}
