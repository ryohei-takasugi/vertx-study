public class Sample1 {
  //インスタンス生成
  Sample2 smp = new Sample2();

  public static void main(String[] args){
      Sample1 s = new Sample1();
      s.caller();
  }

  public void caller(){

      //スレッド1
      new Thread(() -> {
        // Sample2 smp = new Sample2();
        // setting(smp, 10);
        setting(10);
      }).start();

      //スレッド2
      new Thread(() -> {
        // Sample2 smp = new Sample2();
        // setting(smp, 20);
        setting(20);
      }).start();

      //スレッド3
      new Thread(() -> {
        // Sample2 smp = new Sample2();
        // setting(smp, 30);
        setting(30);
      }).start();

      //スレッド4
      new Thread(() -> {
        // Sample2 smp = new Sample2();
        // setting(smp, 40);
        setting(40);
      }).start();

      //スレッド5
      new Thread(() -> {
        // Sample2 smp = new Sample2();
        // setting(smp, 50);
        setting(50);
      }).start();

  }

  // void setting(Sample2 smp, int num){
  void setting(int num){
      //スレッド毎の値をセット。
      smp.setNum(num);
      try {
          //少し待機
          Thread.sleep(1);
      } catch (InterruptedException e) {
          // 適当コード
          e.printStackTrace();
      }
      //スレッドごとの値を出力。
      System.out.println("入力値：" + num + "  結果:" + smp.getNum());
  }
}
