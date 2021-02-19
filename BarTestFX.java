import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
   
   public class BarTestFX extends Application{
    private BreakoutThread breakoutthread;
    public static void main(String[] args){
      launch( args );
    }
    @Override
    public void start( Stage stage ) {
      //key
      Key key = new Key(); //キーボード処理のクラス
      // title
      stage.setTitle( "Breakout Game" );
      
      // pane, scene
      Pane pane = new Pane();
      Scene scene = new Scene( pane );
      stage.setScene( scene );

      scene.setOnKeyPressed(
        new EventHandler<KeyEvent>(){
          public void handle(KeyEvent e){
            key.keyPressed(e);
          }
           });
                scene.setOnKeyReleased(
        new EventHandler<KeyEvent>(){
          public void handle(KeyEvent e){
            key.keyReleased(e);
          }
        });
      
      // canvas, graphicscontext
      Canvas canvas = new Canvas( 640, 480 );
      GraphicsContext gc = canvas.getGraphicsContext2D();
      pane.getChildren().add( canvas );
      
      // breakoutthread
      breakoutthread = new BreakoutThread(gc, key );
      breakoutthread.start();
      
      // show
      stage.show();
    }
  }
  
  
  class BreakoutThread extends AnimationTimer {
    // data
    private GraphicsContext gc;
    private Ball ball;
    private Bar bar;
    
    // method
    public BreakoutThread( GraphicsContext gc, Key key ) {
      this.gc = gc;
      this.ball = new Ball();

      this.bar = new Bar(key);


    }
    
    @Override
    public void handle( long time ) {
      // 1回ぜんぶ消す
      gc.clearRect( 0, 0,  640, 480 );
      
      // 表示する
      ball.move();      // ballの新しい場所を決める
      ball.draw( gc );    // ballを描く
      bar.draw(gc);
      bar.move();
    }
  }
  
  
  class Ball {
    // data
    private int x;
    private int y;
    private int x_speed;
    private int y_speed;
    
    // method
    public Ball() {
      this.x = 20;        
      this.y = 20;
      this.x_speed = 5;
      this.y_speed = 5;
    }
    
    public void move() {      // ballの場所を計算する
      this.x += x_speed;
      this.y += y_speed;
    }
    
    public void draw( GraphicsContext gc ) {
      gc.setFill( Color.BLUE );   // ballの色を決める
      gc.fillOval( x, y, 20, 20 );  // ballを描く
    }
  }
class Key{
   private boolean right ;
   private boolean left ;
   public Key(){
    this.right = false;
    this.left = false;
   }
  
  public void keyPressed( KeyEvent e ){
    switch( e.getCode()){
     
      case RIGHT:
      System.out.println("RIGHT pressed.");
      right = true;
      break;
       case LEFT:
      System.out.println("LEFT pressed.");
     // (boolean right を,true にする)
      left = true;
      break;
      default:
      break;
    }
   }
  public void keyReleased( KeyEvent e){
    switch(e.getCode()){

      case RIGHT:
      System.out.println("RIGHT released.");
      right = false;
      break;
            case LEFT:
      System.out.println("LEFT released.");
      left = false;
     // ( left を false にする )
      

     // ( right を　false にする)
      break;
      default:
      break;

    }
   }
    public boolean isRightPressed(){
  return right;
}
   public boolean isLeftPressed(){
    return left;
   }
}

class Bar{
  private int x;
  private int y;
  private int width;
  private int height;
  private Key  key;
  private int x_speed;



  public Bar(Key key){
    this.x = 100;
    this.y = 470;
    this.width = 100;
    this.height = 10;
    this.key = key;
    this.x_speed = 5;
    }
public void draw( GraphicsContext gc){
  gc.setFill(Color.RED);
  gc.fillRect(x,y,width,height);
  }
  public void move(){
    if(key.isRightPressed() == true ){
      x+=x_speed;
      
    }
    if(key.isLeftPressed()== true){
      x-=x_speed;
    }
}
}