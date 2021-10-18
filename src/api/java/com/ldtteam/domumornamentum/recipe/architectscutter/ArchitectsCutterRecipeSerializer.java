package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArchitectsCutterRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ArchitectsCutterRecipe>
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public @NotNull ArchitectsCutterRecipe fromJson(final @NotNull ResourceLocation recipeId, final JsonObject json)
    {
        if (json.has("nbt") && json.has("block")) {
            try {
                JsonElement element = json.get("nbt");
                CompoundTag nbt;
                if(element.isJsonObject())
                    nbt = TagParser.parseTag(GSON.toJson(element));
                else
                    nbt = TagParser.parseTag(GsonHelper.convertToString(element, "nbt"));

                return new ArchitectsCutterRecipe(recipeId, new ResourceLocation(json.get("block").getAsString()), json.has("count") ? json.get("count").getAsInt() : -1, nbt);
            }
            catch (CommandSyntaxException e)
            {
                LOGGER.warn("Failed to parse NBT from JSON, skipping.");
            }
        }

        return new ArchitectsCutterRecipe(recipeId, json.has("count") ? json.get("count").getAsInt() : -1);
    }

    @Nullable
    @Override
    public ArchitectsCutterRecipe fromNetwork(final @NotNull ResourceLocation recipeId, final @NotNull FriendlyByteBuf buffer)
    {
        return new ArchitectsCutterRecipe(recipeId, buffer.readResourceLocation(), buffer.readInt(), buffer.readNbt());
    }

    @Override
    public void toNetwork(final @NotNull FriendlyByteBuf buffer, final @NotNull ArchitectsCutterRecipe recipe)
    {
        buffer.writeResourceLocation(recipe.getBlockName());
        buffer.writeInt(recipe.getCount());
        buffer.writeNbt(recipe.getAdditionalTag());
    }
}
