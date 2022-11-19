# Vert.x study
Vert.xの勉強環境

## 実装内容

### アーティファクトの機能
1. Http Server の作成
1. 設定ファイルの読み込み、および、設定ファイルをレスポンスする Handler を作成
1. Web client を導入して、外部web-apiからのレスポンスを取得
1. EventBus を導入して、複数の Verticle での通信を実現
1. Template を導入して、HTMLページの作成
1. SessionId をログに表示
1. PUT リクエスト用の処理を作成
1. XML を Json に変換
1. 何らかの Error が発生したときの共通のレスポンスを作成

### 開発環境
1. docker 開発環境の構築
1. gradle init コマンドによるプロジェクト作成（Template未使用）
1. Gradle に eclipse プラグインの導入
1. Gradle に shadowJar プラグインの導入
1. gradle.build ファイルに Vert.x4 (core, web) を導入
1. gradle.build ファイルに logger(slf4j, logback) を導入
1. gradle run を実行可能
1. gradle.build ファイルに Vert.x4 (template) を導入
1. gradle.build ファイルに Vert.x4 (config) を導入
1. gradle.build ファイルに Vert.x4 (Web-client) を導入
1. Gradle に spotless(Code Format Checker & Code Formatter) プラグインの導入
1. Code Format Checker による Google Java Format チェックの導入
1. Gradle に spotbugs(静的解析) プラグインの導入
1. junit5 の導入
1. Gradle に jacoco(コードカバレッジ測定) プラグインの導入
1. Gradle に javafaker(ダミーデータ作成) プラグインの導入

---
* nexus に関する勉強は Gradle-study で実施