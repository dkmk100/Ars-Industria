package com.dkmk100.ars_industria.datagen;

/*
public class ArsProviders {

    public static class GlyphProvider extends GlyphRecipeProvider {

        public GlyphProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            Path output = this.generator.getOutputFolder();

            recipes.add(get(TestEffect.INSTANCE).withItem(Items.DIRT));

            for (GlyphRecipe recipe : recipes) {
                Path path = getScribeGlyphPath(output, recipe.output.getItem());
                DataProvider.save(GSON, cache, recipe.asRecipe(), path);
            }

        }

        @Override
        public String getName() {
            return "Example Glyph Recipes";
        }
    }

    public static class EnchantingAppProvider extends ApparatusRecipeProvider {

        public EnchantingAppProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            Path output = this.generator.getOutputFolder();
            for (EnchantingApparatusRecipe g : recipes){
                if (g != null){
                    Path path = getRecipePath(output, g.getId().getPath());
                    DataProvider.save(GSON, cache, g.asRecipe(), path);
                }
            }

        }

        protected static Path getRecipePath(Path pathIn, String str){
            return pathIn.resolve("data/"+ root +"/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "Example Apparatus";
        }
    }

    public static class ImbuementProvider extends ImbuementRecipeProvider {

        public ImbuementProvider(DataGenerator generatorIn){
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            Path output = generator.getOutputFolder();
            for(ImbuementRecipe g : recipes){
                Path path = getRecipePath(output, g.getId().getPath());
                DataProvider.save(GSON, cache, g.asRecipe(), path);
            }

        }

        protected Path getRecipePath(Path pathIn, String str){
            return pathIn.resolve("data/"+ root +"/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "Example Imbuement";
        }

    }

    @SuppressWarnings("ConstantConditions")
    public static class PatchouliProvider extends com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider {

        public PatchouliProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void run(HashCache cache) throws IOException {

            for (AbstractSpellPart spell : ArsNouveauRegistry.registeredSpells) {
                addGlyphPage(spell);
            }

            //check the superclass for examples

            for (PatchouliPage patchouliPage : pages) {
                DataProvider.save(GSON, cache, patchouliPage.build(), patchouliPage.path());
            }

        }

        @Override
        public void addBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage){
            PatchouliBuilder builder = new PatchouliBuilder(category, item.asItem().getDescriptionId())
                    .withIcon(item.asItem())
                    .withPage(new TextPage(root + ".page." + item.asItem().getRegistryName().getPath()))
                    .withPage(recipePage);
            this.pages.add(new PatchouliPage(builder, getPath(category, item.asItem().getRegistryName().getPath())));
        }

        public void addFamiliarPage(AbstractFamiliarHolder familiarHolder) {
            PatchouliBuilder builder = new PatchouliBuilder(FAMILIARS, "entity."+ root + "." + familiarHolder.getId() + "_familiar")
                    .withIcon("ars_nouveau:familiar_" + familiarHolder.getId())
                    .withTextPage("ars_nouveau.familiar_desc." + familiarHolder.getId())
                    .withPage(new EntityPage(prefix(familiarHolder.getEntityKey() + "_familiar").toString()));
            this.pages.add(new PatchouliPage(builder, getPath(FAMILIARS, familiarHolder.getId())));
        }

        public void addRitualPage(AbstractRitual ritual) {
            PatchouliBuilder builder = new PatchouliBuilder(RITUALS, "item.ars_nouveau.ritual_" + ritual.getID())
                    .withIcon("ars_nouveau:ritual_" + ritual.getID())
                    .withTextPage("ars_nouveau.ritual_desc." + ritual.getID())
                    .withPage(new CraftingPage(root + ":ritual_" + ritual.getID()));

            this.pages.add(new PatchouliPage(builder, getPath(RITUALS, ritual.getID())));
        }

        public void addEnchantmentPage(Enchantment enchantment) {
            PatchouliBuilder builder = new PatchouliBuilder(ENCHANTMENTS, enchantment.getDescriptionId())
                    .withIcon(Items.ENCHANTED_BOOK.getRegistryName().toString())
                    .withTextPage(root + ".enchantment_desc." + enchantment.getRegistryName().getPath());

            for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
                builder.withPage(new EnchantingPage("ars_nouveau:" + enchantment.getRegistryName().getPath() + "_" + i));
            }
            this.pages.add(new PatchouliPage(builder, getPath(ENCHANTMENTS, enchantment.getRegistryName().getPath())));
        }

        @Override
        public String getName() {
            return "Example Patchouli Datagen";
        }

        @Override
        public Path getPath(ResourceLocation category, String fileName) {
            return this.generator.getOutputFolder().resolve("data/"+ root +"/patchouli_books/example/en_us/entries/" + category.getPath() + "/" + fileName + ".json");
        }

        ImbuementPage ImbuementPage(ItemLike item){
            return new ImbuementPage(root + ":imbuement_" + item.asItem().getRegistryName().getPath());
        }

    }

    static String root = ArsIndustria.MODID;
    static ResourceLocation prefix(String path){
        return new ResourceLocation(ArsIndustria.MODID, path);
    }

}
 */
