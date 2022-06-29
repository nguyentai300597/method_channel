import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
// ignore: implementation_imports
import 'package:flutter/src/services/platform_channel.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  static const channel = "checkping";
  final _eventChannel = const EventChannel("stream");
  static const plaftorm = MethodChannel(channel);
  static const _StringCodecChannel = 'StringCodec';
  static const _JSONMessageCodecChannel = 'JSONMessageCodec';
  static const _BinaryCodecChannel = 'BinaryCodec';
  static const _StandardMessageCodecChannel = 'StandardMessageCodec';
  StreamController<String> getdata = StreamController();
  Stream<String> get namedata => getdata.stream;
  Sink<String> get setSink => getdata.sink;
  static const BasicMessageChannel<String> stringPlatform =
      BasicMessageChannel<String>(_StringCodecChannel, StringCodec());

  static const BasicMessageChannel<dynamic> jsonPlatform =
      BasicMessageChannel<dynamic>(
          _JSONMessageCodecChannel, JSONMessageCodec());

  static const BasicMessageChannel<dynamic> standardPlatform =
      BasicMessageChannel<dynamic>(
          _StandardMessageCodecChannel, StandardMessageCodec());

  static const BasicMessageChannel<ByteData> binaryPlatform =
      BasicMessageChannel<ByteData>(_BinaryCodecChannel, BinaryCodec());

  String _message1 = 'empty';
  String _message2 = 'empty';
  String _message3 = 'empty';
  String _message4 = 'empty';
  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _eventChannel.receiveBroadcastStream().listen((event) {
      setSink.add("${event}");
    });
    jsonPlatform.setMessageHandler(_handleJsonPlatformBack);
  }

  @override
  void didUpdateWidget(covariant MyHomePage oldWidget) {
    // TODO: implement didUpdateWidget
    super.didUpdateWidget(oldWidget);
    _fetchNetworkCall();
    // plaftorm.setMethodCallHandler((call) async {
    //   print("arguments${call.arguments}");
    // });
  }

  Future<dynamic> _handleJsonPlatformBack(dynamic response) async {
    setState(() {
      _message2 = response['phone'];
    });
    return "";
  }

  @override
  Widget build(BuildContext context) {
    // final networkStream =
    //     _eventChannel.receiveBroadcastStream().distinct().map((dynamic event) {
    //   print("dataa from native $event");
    //   netw

    // });
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            StreamBuilder<String>(
                stream: namedata,
                initialData: "no data",
                builder: (_, snaphost) {
                  return Text("${snaphost.data.toString()}");
                }),
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              _message2,
              style: Theme.of(context).textTheme.headline4,
            ),
            FutureBuilder<String>(
              future: _fetchNetworkCall(), // async work
              builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
                switch (snapshot.connectionState) {
                  case ConnectionState.waiting:
                    return Text('Loading....');
                  default:
                    if (snapshot.hasError)
                      return Text('Error: ${snapshot.error}');
                    else
                      return Text('Result: ${snapshot.data}');
                }
              },
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _openNativeView,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  Future<void> _openNativeView() async {
    try {
      await plaftorm
          .invokeMethod("activity", "assadsad")
          .then((value) => print("object$value"));
      // plaftorm.setMethodCallHandler((call) async {
      //   print("arguments${call.arguments}");
      // });
      //  await plaftorm.invokeMethod("sendata").then((value) =>print("da vao $value"));
    } on PlatformException catch (e) {
      debugPrint("Error333333: '${e.message}'.");

      print("error ${e.message}");
    }
  }

  Future<String>? _fetchNetworkCall() async {
    try {
      return await plaftorm.invokeMethod("sendata", "assadsad");
      // plaftorm.setMethodCallHandler((call) async {
      //   print("arguments${call.arguments}");
      // });
      //  await plaftorm.invokeMethod("sendata").then((value) =>print("da vao $value"));
    } on PlatformException catch (e) {
      debugPrint("Error333333: '${e.message}'.");

      print("error ${e.message}");
    }
    return "";
  }

  void callNativeJSONMessageCodec() {
    print('callNativeJSONMessageCodec');
    jsonPlatform.send("");
  }
}

// import 'package:flutter/material.dart';
// import 'package:flutter/services.dart';
// import 'package:url_launcher/url_launcher.dart' as launcher;

// void main() => runApp(const MyApp(color: Colors.blue));

// @pragma('vm:entry-point')
// void topMain() => runApp(const MyApp(color: Colors.green));

// @pragma('vm:entry-point')
// void bottomMain() => runApp(const MyApp(color: Colors.purple));

// class MyApp extends StatelessWidget {
//   const MyApp({super.key, required this.color});

//   final MaterialColor color;

//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       title: 'Flutter Demo',
//       theme: ThemeData(
//         primarySwatch: color,
//       ),
//       home: const MyHomePage(title: 'Flutter Demo Home Page'),
//     );
//   }
// }

// class MyHomePage extends StatefulWidget {
//   const MyHomePage({super.key, required this.title});
//   final String title;

//   @override
//   State<MyHomePage> createState() => _MyHomePageState();
// }

// class _MyHomePageState extends State<MyHomePage> {
//   int? _counter = 0;
//   late MethodChannel _channel;

//   @override
//   void initState() {
//     super.initState();
//     _channel = const MethodChannel('multiple-flutters');
//     _channel.setMethodCallHandler((call) async {
//       if (call.method == "setCount") {
//         // A notification that the host platform's data model has been updated.
//         setState(() {
//           _counter = call.arguments as int?;
//         });
//       } else {
//         throw Exception('not implemented ${call.method}');
//       }
//     });
//   }

//   void _incrementCounter() {
//     // Mutations to the data model are forwarded to the host platform.
//     _channel.invokeMethod<void>("incrementCount", _counter);
//   }

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: Text(widget.title),
//       ),
//       body: Center(
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.center,
//           children: [
//             const Text(
//               'You have pushed the button this many times:',
//             ),
//             Text(
//               '$_counter',
//               style: Theme.of(context).textTheme.headline4,
//             ),
//             TextButton(
//               onPressed: _incrementCounter,
//               child: const Text('Add'),
//             ),
//             TextButton(
//               onPressed: () {
//                 _channel.invokeMethod<void>("next", _counter);
//               },
//               child: const Text('Next'),
//             ),
//             ElevatedButton(
//               onPressed: () async {
//                 // Use the url_launcher plugin to open the Flutter docs in
//                 // a browser.
//                 final url = Uri.parse('https://flutter.dev/docs');
//                 if (await launcher.canLaunchUrl(url)) {
//                   await launcher.launchUrl(url);
//                 }
//               },
//               child: const Text('Open Flutter Docs'),
//             ),
//           ],
//         ),
//       ),
//     );
//   }
// }
