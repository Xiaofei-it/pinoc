# Demo of Pinoc

This chapter illustrates a demo of the Pinoc library.

<img src="pics/pinoc_application.png" width="800" height="500"/>

## Deploy Pinoc in the app

1. In the `onCreate` of our application, start a service to download
the configuration from your server.

2. Once the configuration is downloaded, configure Pinoc
in, for instance, the `onSuccess` method of the downloading callback:

```
Pinoc.addJavaDependency(yourJavaLibrary);
Pinoc.addDependency(yourLibrary);
Pinoc.config(configuration);
```

Now we have finished the deployment in our app. Then we release the app.

After a few days, we find there is a bug in our app.
And we also need to track an event.

## An Activity

Our app has a following `Activity`:

```
public class DemoActivity extends AppCompatActivity {

    private void init() {
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(intent.getStringExtra("tmp").toUpperCase());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        init();
        findViewById(R.id.bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

After the app is released, we find that the `Intent` passed to the `DemoActivity` may be `null`.
Also, the key mapping the `String` in the `Intent` is `temp` instead of `tmp`.

Moreover, we have to track the click event in this `Activity`
but forget to add the tracking instructions in the `onClick` method.

## Hotfix and event tracking in Java

To fix the `init` method, we should replace it with the following:

```
private void init() {
    Intent intent = getIntent();
    String tmp = intent.getStringExtra("temp");
    if (tmp != null) {
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(tmp.toUpperCase());
    }
}
```

To track the event, we should insert the following code snippet at the entrance of the `onClick` method:

```
// `this` is the `OnClickListener`. We get its enclosing `Activity`.
Class<?> context = get_outer_context(this);
String id = v.getId();
track(context.getClass().getName(), id);
```

`get_outer_context` will be mentioned later.

## Write code in Zlang

We write the above code snippets in Zlang respectively.

For the `init` method, we write the following:

```
/* The replacement of the init method */
function main(className, methodName, methodSignature, this, parameters) {
   intent = _invoke_method(this, "getIntent");
   tmp = _invoke_public_method(intent, "getStringExtra", "temp");
   if (tmp != null) {
      textView = _invoke_method(this, "findViewById", _get_static_field("com.iqiyi.trojantest.R$id", "tv"));
      _invoke_public_method(textView, "setText", _invoke_public_method(tmp, "toUpperCase"));
   }
   return null;
}
```

For we should replace the original method `init`, we have to make the `main` function have a return value,
which is thus `null` above.

For the `onClick` method, we write the following:

```
/* The instructions to execute at the entrance of the onClick method */
function main(className, methodName, methodSignature, this, parameters) {
   context = get_outer_context(this);
   id = _invoke_public_method(parameters[0], \"getId\");
   track(_get_class_name(context), id);
}
```


The above `get_outer_context` is a Java function provided by Pinoc instead of Zlang.
You may refer to `PinocLibraries` for more provided functions.

## Write the configuration

Now we write the following configuration:

```
{targets:[
    {class: "com/iqiyi/pinocdemo/DemoActivity", method_name: "init", method_sig: "()V", library: 0},
    {class: "com/iqiyi/pinocdemo/DemoActivity$1", method_name: "onClick", method_sig: "(Landroid/view/View;)V", library: 2}
],
libraries:[
"function main(className, methodName, methodSignature, this, parameters) \{\
   intent = _invoke_method(this, \"getIntent\");\
   tmp = _invoke_public_method(intent, \"getStringExtra\", \"temp\");\
   if (tmp != null) \{\
      textView = _invoke_method(this, \"findViewById\", _get_static_field(\"com.iqiyi.trojantest.R$id\", \"tv\"));\
      _invoke_public_method(textView, \"setText\", _invoke_public_method(tmp, \"toUpperCase\"));\
   \}\
   return null;\
\}",
"function main(className, methodName, methodSignature, this, parameters) \{\
   context = get_outer_context(this);\
   id = _invoke_public_method(parameters[0], \"getId\");\
   track(_get_class_name(context), id);\
\}"
]}
```

Note that `{`, `}` and `"` should be escaped in a Json string.

## Configuration dispatch

Now we put the configuration on our server.

And the app will download it to configure Pinoc.

After that, Pinoc will replace the `init` method
and insert the code snippet for event tracking at the entrance of the `onClick` method.