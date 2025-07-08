@Mod(FlanJourneyMapIntegration.MODID)
public class FlanJourneyMapIntegration {
    public static final String MODID = "flanjourneymap";
    private boolean drawClaims = true;

    public FlanJourneyMapIntegration() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
    }

    @SubscribeEvent
    public void onOverlayRegister(RegisterEvent event) {
        event.register(OverlayRenderer.class, OverlayManager.LAYER_JOURNEYMAP::registerRenderer);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (!drawClaims || Minecraft.getInstance().level == null) return;

        ClaimStorage storage = ClaimStorage.get(Minecraft.getInstance().level);
        storage.getClaims().forEach((uuid, claim) -> {
            JourneyMapApi jm = JourneyMapAPI.getInstance();
            Bounds bounds = claim.getBounds();
            jm.setShapeOverlay(
                "flan_claim_" + claim.getUUID(),
                claim.getOwnerName(),
                jm.createRectangle(
                    bounds.minX, bounds.minZ, bounds.maxX, bounds.maxZ
                ),
                Color.GREEN,
                true
            );
        });
    }

    @SubscribeEvent
    public void onClientCommand(ClientChatEvent e) {
        if (e.getMessage().equalsIgnoreCase("/tjmi")) {
            drawClaims = !drawClaims;
            JourneyMapAPI.getInstance().clearShapes(PermissionsOverlay.getShapeGroup());
            Minecraft.getInstance().player.sendMessage(
                Component.literal("Flan claims overlay " + (drawClaims ? "activado" : "desactivado")), Util.NIL_UUID
            );
            e.setCanceled(true);
        }
    }
}
