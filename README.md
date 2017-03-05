# JacocoAndroid
1.实现jacoco Instrumentation操作（后面通过命令直接启动该instrument，最下面有），注意最后启动了 InstrumentedActivity

复制代码
public class JacocoInstrumentation extends Instrumentation

 @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        DEFAULT_COVERAGE_FILE_PATH = "/sdcard/cover.ec";

        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "异常 : " + e);
                e.printStackTrace();
            }
        }
        if (arguments != null) {
            //mCoverage = getBooleanArgument(arguments, "coverage");
            mCoverageFilePath = arguments.getString("coverageFile");
        }

        mIntent = new Intent(getTargetContext(), InstrumentedActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start();
    }
复制代码
 

2.在InstrumentedActivity  onDestroy设置监听，当触发的时候触发生成jacoco覆盖率文件

<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  权限
复制代码
InstrumentedActivity extends MainActivity {
    public static String TAG = "InstrumentedActivity";

    private FinishListener mListener;

    public void setFinishListener(FinishListener listener) {
        mListener = listener;
    }


    @Override
    public void onDestroy() {
        Log.d(TAG + ".InstrumentedActivity", "onDestroy()");
        super.finish();
        if (mListener != null) {
            mListener.onActivityFinished();
        }
    }

}
复制代码
3.触发监听的操作

复制代码
private void generateCoverageReport() {
        Log.d(TAG, "generateCoverageReport():" + getCoverageFilePath());
        OutputStream out = null;
        try {
            out = new FileOutputStream(getCoverageFilePath(), false);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);

            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
复制代码
 

启动：

2.1注册instrumentation,通过instrumentation启动被测应用

adb shell am instrument com.jacs.zhaotang.jscscs/test.JacocoInstrumentation

    <instrumentation
        android:handleProfiling="true"
        android:label="CoverageInstrumentation"
        android:name="test.JacocoInstrumentation"
        android:targetPackage="com.jacs.zhaotang.jscscs"/>
 

3.把生成的jacoco覆盖率文件放在指定文件夹，用gradle生成htmlreport

 将该文件拖至入app根目录/build/outputs/code-coverage/connected下（文件夹没有的话可新建）

 运行gradlew jacocoTestReport

复制代码
task jacocoTestReport(type: JacocoReport) {
    group = "Reporting"
    description = "Generate Jacoco coverage reports after running tests."
    reports {
        xml.enabled = true
        html.enabled = true
    }
    classDirectories = fileTree(
            dir: './build/intermediates/classes/debug',
            excludes: ['**/R*.class',
                       '**/*$InjectAdapter.class',
                       '**/*$ModuleAdapter.class',
                       '**/*$ViewInjector*.class'
            ])
    sourceDirectories = files(coverageSourceDirs)
    executionData = files("$buildDir/outputs/code-coverage/connected/coverage.ec")

    doFirst {
        new File("$buildDir/intermediates/classes/").eachFileRecurse { file ->
            if (file.name.contains('$$')) {
                file.renameTo(file.path.replace('$$', '$'))
            }
        }
    }
}
 

4.report路径：\jscscs\app\build\reports\jacoco\jacocoTestReport


