public class ConfigHolder {
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
            .configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class ClientConfig {
        public final ForgeConfigSpec.BooleanValue showClaims;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            showClaims = builder
                .comment("Mostrar claims de Flan en el mapa")
                .define("showClaims", true);
        }
    }
}
