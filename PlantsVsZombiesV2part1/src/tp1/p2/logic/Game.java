package tp1.p2.logic;

import static tp1.p2.view.Messages.error;

import java.util.Random;

import tp1.p2.logic.gameobjects.Sunflower;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.view.Messages;
import tp1.p2.logic.ZombiesManager;
import tp1.p2.control.Command;
import tp1.p2.control.ExecutionResult;
import tp1.p2.control.Level;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.gameobjects.Peashooter;

public class Game implements GameWorld, GameItem, GameStatus{
	
	private static final int INITIAL_SUNCOINS = 50;
	
	private boolean playerQuits;
	
	private long seed;
	
	private Level level;
	
	private int cycle = 0;
	
	private Random rand; // ver que haCER
	
	private int suncoins = 50;
	
	private ZombiesManager zombiesManager;
	
	private GameObjectContainer container;
	
	private boolean playerDied;
	
	
	public Game(long seed, Level level)
	{
		this.seed = seed;
		this.level = level;
		this.rand = new Random(seed);
		inicializar();
	}
	
	public void inicializar() {
		zombiesManager = new ZombiesManager(this, level, rand);
		container = new GameObjectContainer();
	}
	
	public void reset()
	{
		suncoins = INITIAL_SUNCOINS;
		cycle = 0;
		playerQuits = false;
		playerDied = false;
	}
	
	public boolean isFinished() {
		if(playerQuits || playerDied) return true;
		return false;
	}
	
	public boolean isPlayerDied()
	{
		return playerDied;
	}
	
	public void PlayerQuits() //setter de que el jugador cierra
	{
		playerQuits = true;
	}
	
	
	public boolean isPlayerQuits() {
		return playerQuits;
	}
	
	private static boolean isPositionInLimits(int col, int row) {
		if(col < 0 || col > 8 || row < 0 || row > 4) {
			return false;
		}else {
			return true;
		}
	}

	public boolean execute(Command command) 
	{
		ExecutionResult commandResult = command.execute(this);
		return commandResult.draw();
	}
	
	public boolean addGameObject(GameObject gameObject)
	{
		boolean ok = false;
		int col = gameObject.getCol();
		int row = gameObject.getRow();
		if (!isPositionEmpty(col, row) || !isPositionInLimits(col, row)) 
		{
			System.out.println(error(Messages.INVALID_POSITION));
			ok = false;
		}
		else
		{
			container.add(gameObject);
		}
		return ok;
	     //  si la posición está libre pide al contenedor que lo añada 
	          // container.add(gameObject);
	}
	
	public int getCycle()
	{
		return cycle;
	}
	
	public int getSuncoins()
	{
		return suncoins;
	}
	
	public void addSuncoins(int coins)
	{
		suncoins += coins;
	}
	
	public int getRemainingZombies()
	{
		return zombiesManager.getRemainingZombies();
	}
	
	public boolean tryToBuy (int coins)
	{
		boolean ok;
		if(coins >= suncoins) 
		{
			suncoins -= coins;
			ok = true;
		}
		else
		{
			System.out.println(Messages.NOT_ENOUGH_COINS); // en caso de que el usuario no tenga las monedas necesarias para comprar
			ok = false;
		}
		return ok;
	}
	
	public boolean zombieDied()
	{
		
		return zombiesManager.zombieDied();
	}
	
	public boolean isValidZombiePosition(Zombie zombie)
	{
		return true;//isPositionInLimits(/*poner aqui el row y col de zombie*/);
	}
	
	public boolean allZombiesDied()
	{
		return zombiesManager.allZombiesDied();
	}
	
	public boolean receiveZombieAttack(int damage)
	{
		//rellenar
		return true;
	}
	
	public boolean receivePlantAttack(int damage)
	{
		return true;
	}

	@Override
	public String positionToString(int col, int row) {
		
		return null;
	}

	@Override
	public boolean isNpc() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean playerQuits() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		container.update();
		zombiesManager.update();
		
	}

	@Override
	public boolean isPositionEmpty(int col, int row) {
		// true si esa posicion est� libre en todas las listas
		boolean ok = false;
		if(container.isPositionEmpty(col, row)) //ver si hay zombies
		{
			ok= true;
		}
		return ok;
	}

	@Override
	public void attackPlant(int col, int row, int damage) {
		
	}

	@Override
	public void attackZombie(int col, int row, int damage) {
		
	}

	@Override
	public void addGameObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ZombieArrived() {
		return false;
	}

	@Override
	public boolean ZombieDied() {
		if(zombiesManager.zombieDied()) return true;
		return false;
	}

	@Override
	public boolean isValidPlantPosition(int col, int row) {
		if(isPositionEmpty(col, row) && isPositionInLimits(col, row)) return true;
		return false;
	}

	@Override
	public boolean isValidZombiePosition(int col, int row) {
		// TODO Auto-generated method stub
		return false;
	}
}
