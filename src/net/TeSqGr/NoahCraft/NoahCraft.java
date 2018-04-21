package net.TeSqGr.NoahCraft;

import java.util.logging.Logger;

import net.TeSqGr.NoahCraft.Entity.Camera;
import net.TeSqGr.NoahCraft.Entity.Player.Player;
import net.TeSqGr.NoahCraft.Input.Input;
import net.TeSqGr.NoahCraft.Input.KeyboardHandler;
import net.TeSqGr.NoahCraft.Input.MouseInput;
import net.TeSqGr.NoahCraft.Rendering.Renderer;
import net.TeSqGr.NoahCraft.Rendering.Skybox;
import net.TeSqGr.NoahCraft.Timing.Timing;
import net.TeSqGr.NoahCraft.Window.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFWErrorCallback.createPrint;
import static org.lwjgl.system.MemoryUtil.*;

import net.TeSqGr.NoahCraft.World.Chunk;
import net.TeSqGr.NoahCraft.World.Coordinate;
import net.TeSqGr.NoahCraft.World.Noise;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class NoahCraft {

    public Logger noahLogger = Logger.getLogger("Noah L");

    public GLFWErrorCallback errorCallback;

    public Window window;
    public Renderer renderer;
    public Timing timer;
    public Input input;
    public MouseInput mouseInput;

    public Camera getCamera() {
        return camera;
    }

    private Camera camera;
    public Player testPlayer = new Player(1, new Coordinate(0, 0, 0));


    //DELET THIS
    //WorldFiller w = new WorldFiller();


    public static void main(String[] args) {
        new NoahCraft();
        instance.noahLogger.exiting("NoahCraft", "Main");
    }


    public static NoahCraft instance;

    public NoahCraft() {
        if (instance == null) {
            instance = this;
        }
        init();
        gameLoop();
        dispose();
    }

    private void init() {
        glfwSetErrorCallback(errorCallback = createPrint(System.err));

        glfwInit();
        window = new Window(640, 480, "NoahCraft");
        renderer = new Renderer(window);
        timer = new Timing();
        input = new Input();
        mouseInput = new MouseInput();
        mouseInput.init(window);
        camera = new Camera();
        window.visible(true);
        renderer.addChunk(Chunk.genChunk(32,32), 3, 3);
    }

    public void dispose() {
        window.visible(false);
        renderer.dispose();
        window.dispose();
    }

    private void gameLoop() {
        double delta, accumulator = 0.0, interval = 1.0 / 20.0, alpha;
        timer.init();
        while (!window.shouldClose()) {
            timer.update();
            delta = timer.delta();
            accumulator += delta;
            input();
            while (accumulator > interval) {
                update(delta);
                accumulator -= interval;
            }
            render();
            System.out.println("FPS:" + timer.getFPS());
        }
    }

    public void input() {
        glfwPollEvents();
        mouseInput.input(window);
        input.input(window);

    }

    public void update(double delta) {
        timer.updateUPS();
        input.update(mouseInput);
    }

    public void render() {
        renderer.render(window, camera);
        window.render();
        testPlayer.update();
        timer.updateFPS();
    }

}
