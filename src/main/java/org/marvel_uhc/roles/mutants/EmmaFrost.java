package org.marvel_uhc.roles.mutants;

import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class EmmaFrost extends Role {

    public EmmaFrost() {
        super("Emma Frost", Camp.MUTANTS, "§e");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7."
        );
    }

    @Override
    protected void setupKit() {
        // Kit à définir plus tard
    }
}
