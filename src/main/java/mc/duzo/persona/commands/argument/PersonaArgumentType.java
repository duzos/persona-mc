package mc.duzo.persona.commands.argument;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.common.persona.PersonaRegistry;

public class PersonaArgumentType implements ArgumentType<AbstractPersona> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");

    public static PersonaArgumentType persona() {
        return new PersonaArgumentType();
    }

    public static AbstractPersona getPersona(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, AbstractPersona.class);
    }

    public AbstractPersona parse(StringReader stringReader) throws CommandSyntaxException {
        Identifier id = Identifier.fromCommandInput(stringReader);
        AbstractPersona found = PersonaRegistry.get(id);

        if (found == null) throw new SimpleCommandExceptionType(Text.literal("Persona not found in registry")).create();

        return found;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(PersonaRegistry.REGISTRY.stream().map(AbstractPersona::id).map(Identifier::toString), builder);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}