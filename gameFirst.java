package com.mygdx.game;

import java.util.ArrayList;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.me.MyGdxGame.Attacks.BulletWorks;
import com.me.MyGdxGame.entities.CollisionObjects;
import com.me.MyGdxGame.entities.Player;
import com.me.MyGdxGame.entities.playerPositions;
import com.me.MyGdxGame.entities.positions;

public class gameFirst implements Screen {
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	RayHandler rayHandler;   
	World world;
	//private World world;
	//private RayHandler handler;
    private TiledMap map;	 
    private CollisionObjects CO;
    private ArrayList<playerPositions> allPlayers = new ArrayList<playerPositions>();
    //private Texture Texture2;
    private TextureAtlas textureAtlas;
    private Player player;
    private SpriteBatch batch;  
    private OrthogonalTiledMapRenderer renderer;
    //private MyGestureHandler gestureHandler;
    float animationTime = 0;
    boolean animationPlaying = true;
    boolean animationPlaying2 = true;
    public Animation walking;
    public Animation walkingLeft;
    public Animation walkingRight;
    public Animation walkingUp;
    int X = 400;
    int currentDirection = 0;
    int bulletDirection = 0;
    ShapeRenderer sr;
    //modes
    ArrayList<BulletWorks> bullets = new ArrayList<BulletWorks>();
    public boolean leftMode = false;
    public boolean frontMode = true;
    public boolean rightMode = false;
    public boolean upMode = false;
    public ArrayList<positions> pos = new ArrayList<positions>();
    public float zoom = 0.1f;
    public float xposCamera = 0f;
    public float yposCamera = 0f;
    boolean keyProcessed = true;
    boolean play = false;
    private float currentPosx = 400;
    private float currentPosy = 400;
	public gameFirst(GameTrial game) {
		
	}

	@Override
	public void show() {
		TmxMapLoader loader = new TmxMapLoader();
	    map = loader.load("data/WhiteWater.tmx");
	    //world = new World(new Vector2(0, 0), true);
	    //handler = new RayHandler(world);
	    
	    player = new Player("prithvi2502", "currymonster69");
	    CO = new CollisionObjects();
	    allPlayers.add(CO.addPositionPlayers(player));
	    batch = new SpriteBatch();
	    camera = new OrthographicCamera();
	    //handler.setCombinedMatrix(camera.combined);
	    renderer = new OrthogonalTiledMapRenderer(map);
	    sr = new ShapeRenderer();
	   //new PointLight(handler, 5000, Color.CYAN, 100, (1920 / 2)-50, (1980 / 2)+50);
	    world = new World(new Vector2(0, 0), true);
	      rayHandler = new RayHandler(world);
	      rayHandler.setCulling(true);
	      rayHandler.setCombinedMatrix(camera.combined);      
	      rayHandler.useDiffuseLight(true);
	      rayHandler.setAmbientLight(0.2f, 0.2f, 0.2f,1.0f);
	}
	

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.zoom = .5f;
	    renderer.render();
	    camera.update();
	    renderer.setView(camera);
	    batch.begin();
	    
	    update();
	   //handler.setCombinedMatrix(camera.c2ombined);
	   //handler.updateAndRender();
	    
	    allPlayers = CO.updatePlayerPositions(player.getPlayerID(), player.getPlayerCurrentPosX(), player.getPlayerCurrentPosY(), allPlayers);
	    currentPosx =  player.getPlayerCurrentPosX();
    	currentPosy = player.getPlayerCurrentPosY();
	    for(int i=0; i < allPlayers.size(); i++) {
	    	System.out.println(allPlayers.get(i).getPlayerID() + " " + allPlayers.get(i).getX() + " " + allPlayers.get(i).getY());
	    	//currentPosx = allPlayers.get(i).getX();
	    	//currentPosy = allPlayers.get(i).getY();
	    }
	 		
	    for (int i = 0; i < bullets.size(); i++) {
	    	if(bullets.get(i).getDirection() == 0) {
	    		bullets.get(i).updateButtom();
		 		if(bullets.get(i).didCollide(allPlayers) == true) {
		 			System.out.println("HIT");
		 			bullets.remove(i);
		 		} else if(bullets.get(i).getY() > 0 && bullets.get(i).getY() < 1080) {
		 			bullets.get(i).draw(batch);
		 		}else {
		 		
		 			bullets.remove(i);
		 		}
	    	} else if(bullets.get(i).getDirection() == 1) {
	    		bullets.get(i).updateLeft();
		 		if(bullets.get(i).didCollide(allPlayers) == true) {
		 			System.out.println("HIT");
		 			bullets.remove(i);
		 		} else if(bullets.get(i).getX() > 0 && bullets.get(i).getX() < 1920) {
		 			bullets.get(i).draw(batch);
		 		} else {
		 			bullets.remove(i);
		 		}
	    	} else if(bullets.get(i).getDirection() == 2) {
	    		bullets.get(i).updateRight();
		 		if(bullets.get(i).didCollide(allPlayers) == true) {
		 			System.out.println("HIT");
		 			bullets.remove(i);
		 		} else if(bullets.get(i).getX() > 0 && bullets.get(i).getX() < 1920) {
		 			bullets.get(i).draw(batch);
		 		} else {
		 			bullets.remove(i);
		 		}
		 		//System.out.println(bullets.get(i).getxDep() + " " + bullets.get(i).getyDep());
	    	} else if(bullets.get(i).getDirection() == 3) {
	    		bullets.get(i).updateTop();
		 		if(bullets.get(i).didCollide(allPlayers) == true) {
		 			System.out.println("HIT");
		 			bullets.remove(i);
		 		} else if(bullets.get(i).getY() > 0 && bullets.get(i).getY() < 1080) {
		 			bullets.get(i).draw(batch);
		 		} else {
		 			bullets.remove(i);
		 		}
	    	}
    		
	    }

	    Color clr3 = new Color(0.5f,0.8f,0.2f, 0.9f);
	    PointLight pt = new PointLight(rayHandler, 65, clr3, 100, 844, 326); 
	      pt.setStaticLight(true);
	   	    	
	    batch.end();
	    
	      rayHandler.updateAndRender();
	   // System.out.println(camera.position.x + " " + camera.position.y);
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Keys.A) ){
	       player.moveLeft(camera, batch);
	       currentDirection = 2;
	    }else if(Gdx.input.isKeyPressed(Keys.D) ){
	    	player.moveRight(camera, batch);
	    	currentDirection = 1;
	    }else if(Gdx.input.isKeyPressed(Keys.S) ){
	    	player.moveDown(camera, batch);
	    	currentDirection = 0;
	    }else if(Gdx.input.isKeyPressed(Keys.W) ){
	    	player.moveUp(camera, batch);
	    	currentDirection = 3;
	    }else if(Gdx.input.isKeyPressed(Keys.T) ){
	    	BulletWorks BW = new BulletWorks((1920/2) + 300, 1080/2, (int) currentPosx, (int) currentPosy);
	    	bulletDirection = 2;
	    	BW.setDirection(bulletDirection);
	 	    bullets.add(BW);
	    	
	    }else if(Gdx.input.isKeyPressed(Keys.SPACE)){
	    	BulletWorks BW = new BulletWorks(1920/2, 1080/2, (int) currentPosx, (int) currentPosy);
	    	
	 	    player.shoot(camera, batch);
	 	    if(currentDirection == 0){
	 	    	bulletDirection = 0;
	    		
	    	}else if(currentDirection == 1){
	    		bulletDirection = 1;
	    		
	    	}else if(currentDirection == 2){
	    		bulletDirection = 2;
	    		
	    	}else if(currentDirection == 3){
	    		bulletDirection = 3;
	    		
	    	}
	 	    BW.setDirection(bulletDirection);
	 	    bullets.add(BW);
	    }else{
	    	
	    	if(player.frontMode){
	    		setGameToNormal(player.getTexture());
	    		
	    	}else if(player.rightMode){
	    		setGameToNormal(player.getTexture3());
	    		
	    	}else if(player.leftMode){
	    		setGameToNormal(player.getTexture2());
	    		
	    	}else if(player.upMode){
	    		setGameToNormal(player.getTexture4());
	    		
	    	}
	 	    play = false;
	    }      
		
	}
	private void setGameToNormal(TextureRegion texture) {
		batch.draw(texture, 1920/2, 1080/2);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, 1920, 1080);
	}

	

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		world.dispose();
		mapRenderer.dispose();
		map.dispose();
		batch.dispose();
		textureAtlas.dispose();
	}

}
