# Demo of Trojan

This chapter illustrates a demo of the Trojan library.

## An Activity

In our app, there is an `Activity`:

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
    }
}

```

After the app is released, we find that the `Intent` passed to the `DemoActivity` may be `null`.
Also, the key which maps the `String` in the `Intent` is `temp` instead of `tmp`.

## Hotfix

We want to replace `init` with the following:

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

## Write code in Zlang

```
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

Since we want to replace the original method, we must give the method a return value, which is `null` above.

## Write the configuration

We write the configuration:
```
{targets:[
    {class: "com/iqiyi/trojantest/DemoActivity", method_name: "init", method_sig: "()V", library: 0}
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
\}"
]}
```
