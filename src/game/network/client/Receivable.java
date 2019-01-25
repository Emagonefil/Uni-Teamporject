package game.network.client;

import java.util.List;

import game.entity.Entity;

public interface Receivable {

	public void receive(List<Entity> list);
}
