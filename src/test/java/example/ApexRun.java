/*
 * MIT License
 *
 * Copyright (c) 2020 1619kHz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package example;

import example.bean.User;
import org.apex.Apex;
import org.apex.ApexContext;
import org.apex.Injector;
import org.apex.TypeInjector;
import org.apex.annotation.ConfigBean;
import org.apex.annotation.PropertyBean;
import org.apex.annotation.Scheduled;
import org.apex.annotation.Singleton;

/**
 * @author WangYi
 * @since 2020/6/22
 */
public class ApexRun {
  public static void main(String[] args) throws Exception {
    Apex apex = Apex.of();
    apex.packages().add("example");
    apex.mainArgs(args);

    apex.typeAnnotation(ConfigBean.class);
    apex.typeAnnotation(PropertyBean.class);
    apex.typeAnnotation(Singleton.class);
    apex.typeAnnotation(Scheduled.class);

    apex.implInterface(Injector.class);
    apex.implInterface(TypeInjector.class);

    ApexContext apeContext = ApexContext.of();
    apeContext.init(apex);

    User bean = apeContext.getBean(User.class);
    bean.user();
  }
}
