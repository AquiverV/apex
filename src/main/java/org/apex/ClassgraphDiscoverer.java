/*
 * MIT License
 *
 * Copyright (c) 2019 1619kHz
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
package org.apex;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public final class ClassgraphDiscoverer implements Discoverer {

  private final Options classgraphOptions;
  private final ClassGraph classGraph;

  public ClassgraphDiscoverer(Options classgraphOptions) {
    this.classgraphOptions = classgraphOptions;
    this.classGraph = new ClassGraph();
  }

  @Override
  public ScanResult discover(String scanPackageName) {
    this.classGraph.enableAllInfo();
    if (classgraphOptions != null) {
      this.scanPackages(classgraphOptions.getScanPackages(), scanPackageName)
              .skipPackages(classgraphOptions.getSkipPackages())
              .verbose(classgraphOptions.isVerbose())
              .enableRealtimeLogging(classgraphOptions.isEnableRealtimeLogging());
    }
    return this.classGraph.scan();
  }

  /**
   * Set whiteList
   *
   * @param scanPackages
   * @param scanPackageName
   * @return
   */
  private ClassgraphDiscoverer scanPackages(Set<String> scanPackages, String scanPackageName) {
    if (Objects.isNull(scanPackageName) || !scanPackageName.contains(".")) {
      throw new IllegalArgumentException("scanPackageName cannot be empty and needs to conform to the specification");
    }
    if (Objects.isNull(scanPackages)) {
      scanPackages = new LinkedHashSet<>();
    }
    scanPackages.add(scanPackageName);
    this.classGraph.whitelistPackages(scanPackages.toArray(new String[0]));
    return this;
  }

  /**
   * Set blacklist
   *
   * @param blackList
   * @return
   */
  private ClassgraphDiscoverer skipPackages(Set<String> blackList) {
    if (blackList != null && blackList.size() > 0) {
      this.classGraph.blacklistPackages(blackList.toArray(new String[0]));
    }
    return this;
  }

  /**
   * Turn on verbose logging
   *
   * @param verbose
   * @return
   */
  private ClassgraphDiscoverer verbose(boolean verbose) {
    if (verbose) {
      this.classGraph.verbose();
    }
    return this;
  }

  /**
   * Enable real-time logging
   *
   * @param enableRealtimeLogging
   */
  private void enableRealtimeLogging(boolean enableRealtimeLogging) {
    if (enableRealtimeLogging) {
      this.classGraph.enableRealtimeLogging();
    }
  }
}
