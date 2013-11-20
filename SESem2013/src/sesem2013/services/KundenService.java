package sesem2013.services;

import java.util.List;

import sesem2013.entities.Kunde;

public interface KundenService {

	public abstract void create(Kunde kunde);

	public abstract void update(Kunde kunde);

	public abstract void delete(Kunde kunde);

	public abstract List<Kunde> find(Kunde kunde);

}