package de.dytanic.cloudnet.driver.service;

import de.dytanic.cloudnet.common.INameable;
import de.dytanic.cloudnet.common.Validate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ServiceTemplate implements INameable {

    private final String prefix, name, storage;
    private boolean alwaysCopyToStaticServices;

    public ServiceTemplate(String prefix, String name, String storage) {
        Validate.checkNotNull(prefix);
        Validate.checkNotNull(name);
        Validate.checkNotNull(storage);

        this.prefix = prefix;
        this.name = name;
        this.storage = storage;
    }

    public ServiceTemplate(String prefix, String name, String storage, boolean alwaysCopyToStaticServices) {
        this(prefix, name, storage);
        this.alwaysCopyToStaticServices = alwaysCopyToStaticServices;
    }

    public boolean shouldAlwaysCopyToStaticServices() {
        return this.alwaysCopyToStaticServices;
    }

    @Override
    public String toString() {
        return this.storage + ":" + this.prefix + "/" + this.name;
    }

    public String getTemplatePath() {
        return this.prefix + "/" + this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getName() {
        return this.name;
    }

    public String getStorage() {
        return this.storage;
    }

    public static ServiceTemplate parse(String template) {
        String[] base = template.split(":");

        if (base.length > 2) {
            return null;
        }

        String path = base.length == 2 ? base[1] : base[0];
        String storage = base.length == 2 ? base[0] : "local";
        String[] splitPath = path.split("/");

        if (splitPath.length != 2) {
            return null;
        }

        return new ServiceTemplate(splitPath[0], splitPath[1], storage);
    }
}