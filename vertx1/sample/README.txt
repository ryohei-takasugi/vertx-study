スレッドセーフの勉強用

Javaのスレッドにおいて、どこで作ったインスタンスがスレッドセーフにならないのか確認した。
Vert.xでいうとHandlerのhandleメソッドがスレッド化するようで、各HandlerでClass変数を作成するときには注意が必要なことがわかった

[root@Gradle:~/projects/vertx1/sample 12:52 PM]$ javac Sample1.java Sample2.java 
[root@Gradle:~/projects/vertx1/sample 12:53 PM]$ java Sample1
入力値：50  結果:50
入力値：20  結果:40
入力値：40  結果:50
入力値：10  結果:40
入力値：30  結果:50

