package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Map implements Screen {
    Texture img;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture back;
    private Texture welcomeBack;
    private Texture citadel;
    private Texture not_opened_miner;
    private Texture not_opened_miner1;
    private Texture not_opened_miner2;
    private Texture opened_miner;
    private Texture opened_miner1;
    private Texture opened_miner2;
    private Texture stock;
    private Texture arrowDown;
    private Texture arrowDown1;
    private Texture arrowDown2;
    private BitmapFont font;
    private Texture copper;
    private Texture dollars;
    private Texture gold;
    private Texture iron;
    private boolean showWelcomeMessage;
    private long startTime;
    private float mapX = 0;
    private float mapY = 0;
    private Texture cellTexture;
    private Texture rocksTexture;
    private int mapWidth = 40; // Ширина острова в клетках
    private int mapHeight = 80; // Высота острова в клетках
    private int[][] islandMap; // Карта острова, где каждое значение представляет тип клетки (например, вода, земля и т. д.)
    private float cellSize = 150; // Размер клетки
    private Vector2 touch1 = new Vector2();
    private Vector2 touch2 = new Vector2();
    private float initialDistance;
    private EmptyWindow emptyWindow;
    private InputProcessor previousInputProcessor;
    private boolean flag;
    private MapInputProcessor inputProcessor;
    private int numberBuilding;
    private boolean build_flag1 = false;
    private boolean build_flag2 = false;
    private boolean build_flag3 = false;
    // private boolean isWindowOpen;

    public Map(){
        init();
    }

    private void init(){
        inputProcessor = new MapInputProcessor(mapX, mapY);
        Gdx.input.setInputProcessor(inputProcessor);

        batch = new SpriteBatch();
        // Создание камеры с параметрами экрана
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0); // Установка начальной позиции камеры в центр экрана
        camera.update(); // Обновление камеры
        //	back = new Texture(Gdx.files.internal("back.png"));
        cellTexture = new Texture(Gdx.files.internal("cell.png")); // Загрузка текстуры клетки
        cellTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest); // Установка параметра фильтрации
        rocksTexture = new Texture(Gdx.files.internal("rocks.png"));
        rocksTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest); // Установка параметра фильтрации
        welcomeBack = new Texture(Gdx.files.internal("welcome_back.png"));
        citadel = new Texture(Gdx.files.internal("base.png"));
        not_opened_miner = new Texture(Gdx.files.internal("not_opened_miner.png"));
        not_opened_miner1 = new Texture(Gdx.files.internal("not_opened_miner.png"));
        not_opened_miner2 = new Texture(Gdx.files.internal("not_opened_miner.png"));
        opened_miner = new Texture(Gdx.files.internal("opened_miner.png"));
        opened_miner1 = new Texture(Gdx.files.internal("opened_miner.png"));
        opened_miner2 = new Texture(Gdx.files.internal("opened_miner.png"));
        stock = new Texture(Gdx.files.internal("stock.png"));
        arrowDown = new Texture(Gdx.files.internal("arrowDown.png"));
        arrowDown1 = new Texture(Gdx.files.internal("arrowDown.png"));
        arrowDown2 = new Texture(Gdx.files.internal("arrowDown.png"));
        copper = new Texture(Gdx.files.internal("resources/copper.png"));
        dollars = new Texture(Gdx.files.internal("resources/dollars.png"));
        gold = new Texture(Gdx.files.internal("resources/gold.png"));
        iron = new Texture(Gdx.files.internal("resources/iron.png"));
        font = new BitmapFont();
        font.getData().setScale(3f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        showWelcomeMessage = true;
        startTime = System.currentTimeMillis();
        emptyWindow = new EmptyWindow();

        // Инициализация карты острова (здесь просто заполняем всю карту землей)
        islandMap = new int[mapWidth][mapHeight];
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                islandMap[x][y] = (Math.random() < 0.030) ? 0 : 1; // Предполагаем, что 1 - это тип "rocks", 0 - это тип "grass"
            }
        }
        // Проход по карте для группировки клеток типа "rocks"
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (islandMap[x][y] == 1) {
                    // Проверка соседних клеток
                    boolean hasNeighbourRock = false;
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx != 0 || dy != 0) { // Пропускаем текущую клетку
                                int nx = x + dx;
                                int ny = y + dy;
                                if (nx >= 0 && nx < mapWidth && ny >= 0 && ny < mapHeight && islandMap[nx][ny] == 1) {
                                    hasNeighbourRock = true;
                                    break;
                                }
                            }
                        }
                        if (hasNeighbourRock) {
                            break;
                        }
                    }
                    // Если у клетки типа "rocks" есть соседи типа "rocks", она остается типом "rocks", иначе становится типом "grass"
                    if (!hasNeighbourRock) {
                        islandMap[x][y] = 0;
                    }
                }
            }
        }
        // Добавляем обработчик ввода для обработки свайпов
        camera.position.set(mapWidth * cellSize / 2, mapHeight * cellSize / 2, 0);
        camera.update();
    }

    @Override
    public void show() {
    }

    private float arrowOffsetY = 0;
    private float arrowSpeed = 50;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (showWelcomeMessage && System.currentTimeMillis() - startTime < 5000) {
            batch.begin();
            batch.draw(welcomeBack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            font.draw(batch, "Welcome to the Game!", Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200);
            batch.end();
        } else {
            showWelcomeMessage = false;
            batch.setProjectionMatrix(camera.combined); // Установка матрицы проекции камеры для SpriteBatch
            batch.begin();
            mapX = inputProcessor.getMapX();
            mapY = inputProcessor.getMapY();
            // Отрисовка острова из клеток
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    float offsetX = (y % 2 == 0) ? 0 : cellSize / 2; // Смещение для каждой второй строки
                    float offsetY = ((y * cellSize * 0.5f) * 3) / 4;
                    if (islandMap[x][y] == 1) {
                        batch.draw(cellTexture, x * cellSize /*'* 0.75f*/ + offsetX + mapX, offsetY + mapY, cellSize, (cellSize * 3) / 4 );
                    }
                    if (islandMap[x][y] == 0) {
                        batch.draw(cellTexture, x * cellSize /*'* 0.75f*/ + offsetX + mapX, offsetY + mapY, cellSize, (cellSize * 3) / 4);
                        batch.draw(rocksTexture, x * cellSize /*Как'* 0.75f*/ + offsetX + mapX, offsetY + mapY, cellSize,(cellSize * 3 ) / 4);
                    }
                }
            }
            float centerX = 5 * cellSize * 0.75f + ((5 % 2 == 0) ? 0 : cellSize) + mapX;
            float centerY = cellSize * 0.5f + mapY;;
            batch.draw(citadel, centerX + cellSize * 8, centerY + cellSize * 7, citadel.getWidth(), citadel.getHeight());
            batch.end();
            batch.begin();
            if (!build_flag1){
                batch.draw(not_opened_miner, centerX - cellSize * 3, centerY + cellSize * 1, citadel.getWidth(), citadel.getHeight());
                batch.draw(arrowDown, centerX - cellSize * 0, centerY + cellSize * 7 + arrowOffsetY, citadel.getWidth() / 6, citadel.getHeight() / 6);
                // batch.draw(arrowDown, centerX - cellSize * 0, centerY + cellSize * 7, citadel.getWidth()/6, citadel.getHeight()/6);
            }
            if(!build_flag2){
                batch.draw(not_opened_miner1, centerX + cellSize * 12, centerY + cellSize * 15, citadel.getWidth(), citadel.getHeight());
                batch.draw(arrowDown1, centerX + cellSize * 15, centerY + cellSize * 21 + arrowOffsetY, citadel.getWidth() / 6, citadel.getHeight() / 6);
                // batch.draw(arrowDown1, centerX + cellSize * 15, centerY + cellSize * 21, citadel.getWidth()/6, citadel.getHeight()/6);
            }
            if(!build_flag3){
                batch.draw(not_opened_miner2, centerX + cellSize * 1, centerY + cellSize * 12, citadel.getWidth(), citadel.getHeight());
                batch.draw(arrowDown2, centerX + cellSize * 4, centerY + cellSize * 18 + arrowOffsetY, citadel.getWidth() / 6, citadel.getHeight() / 6);
                // batch.draw(arrowDown2, centerX + cellSize * 4, centerY + cellSize * 18, citadel.getWidth()/6, citadel.getHeight()/6);
            }

            if (emptyWindow.great_push == 1){
                if (build_flag1){
                    batch.draw(opened_miner, centerX - cellSize * 3, centerY + cellSize * 1, citadel.getWidth(), (citadel.getWidth() * 5)/4);
                }
                if(build_flag2){
                    batch.draw(opened_miner1, centerX + cellSize * 12, centerY + cellSize * 15, citadel.getWidth(), (citadel.getHeight() * 5)/4);
                }
                if(build_flag3){
                    batch.draw(opened_miner2, centerX + cellSize * 1, centerY + cellSize * 12, citadel.getWidth(), (citadel.getHeight() * 5)/4);
                }
            }
            batch.end();
            batch.begin();


            batch.draw(stock, centerX + cellSize * 15, centerY + cellSize * 9, citadel.getWidth() * 1.25f, citadel.getHeight());

            arrowOffsetY += arrowSpeed * delta;
            if (arrowOffsetY > 10 || arrowOffsetY < -10) {
                arrowSpeed *= -1; // Изменение направления движения при достижении пределов смещения
            }
            if (Gdx.input.justTouched() && !emptyWindow.isWindowOpen) {
                Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touch); // Преобразование экранных координат в мировые
                float touchX = touch.x;
                float touchY = touch.y;
                //ЭТО КООРДИНАТЫ НИЖНЕЙ ДОБЫВАЛКИ
                float buildingX = centerX - cellSize * 3 ; // Позиция X здания на экране
                float buildingY = centerY + cellSize * 1 ; // Позиция Y здания на экране
                float buildingWidth = citadel.getWidth(); // Ширина текстуры здания
                float buildingHeight = citadel.getHeight(); // Высота текстуры здания
                if (touchX >= buildingX && touchX <= buildingX + buildingWidth &&
                        touchY >= buildingY && touchY <= buildingY + buildingHeight) {
                    numberBuilding = 1;
                    build_flag1 = true;
                    emptyWindow.set_cost(numberBuilding);
                    emptyWindow.show();
                }
                buildingX = centerX + cellSize * 12 ;
                buildingY = centerY + cellSize * 15 ;
                if (touchX >= buildingX && touchX <= buildingX + buildingWidth &&
                        touchY >= buildingY && touchY <= buildingY + buildingHeight) {
                    numberBuilding = 2;
                    build_flag2 = true;
                    emptyWindow.set_cost(numberBuilding);
                    emptyWindow.show();
                }
                buildingX = centerX + cellSize * 1 ;
                buildingY = centerY + cellSize * 12 ;
                if (touchX >= buildingX && touchX <= buildingX + buildingWidth &&
                        touchY >= buildingY && touchY <= buildingY + buildingHeight) {
                    numberBuilding = 3;
                    build_flag3 = true;
                    emptyWindow.set_cost(numberBuilding);
                    emptyWindow.show();
                }
            }

// В вашем методе render() также обновляйте и отрисовывайте emptyWindow, если оно открыто
            if (emptyWindow.isWindowOpen) {
                Gdx.input.setInputProcessor(emptyWindow);
                emptyWindow.act(delta);
                emptyWindow.draw();
                flag = true;
                // System.out.println("1");
                // emptyWindow.isWindowOpen = false;
            }
            if (!emptyWindow.isWindowOpen && flag){
                Gdx.input.setInputProcessor(inputProcessor);
            }

            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Сброс камеры
            font.draw(batch, "Pre-alpha version", Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 50);
            batch.draw(dollars, Gdx.graphics.getWidth() + 400, Gdx.graphics.getHeight() - 50);

            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
                // Обработка мультитача для масштабирования
                touch1.set(Gdx.input.getX(0), Gdx.input.getY(0));
                touch2.set(Gdx.input.getX(1), Gdx.input.getY(1));

                float distance = touch1.dst(touch2);

                if (!Gdx.input.justTouched()) {
                    if (initialDistance != 0) {
                        float scale = initialDistance / distance;
                        camera.zoom *= scale;
                        camera.update();
                    }

                    initialDistance = distance;
                } else {
                    initialDistance = distance;
                }
            } else {
                initialDistance = 0;
            }
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }





    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        cellTexture.dispose();
        welcomeBack.dispose();
        citadel.dispose();
        not_opened_miner.dispose();
        not_opened_miner1.dispose();
        not_opened_miner2.dispose();
        opened_miner.dispose();
        opened_miner1.dispose();
        opened_miner2.dispose();
        arrowDown.dispose();
        arrowDown1.dispose();
        arrowDown2.dispose();
        copper.dispose();
        dollars.dispose();
        gold.dispose();
        iron.dispose();
        stock.dispose();
        font.dispose();
    }
}